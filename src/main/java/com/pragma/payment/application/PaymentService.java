package com.pragma.payment.application;


import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import com.pragma.payment.domain.model.PaymentResponse;
import com.pragma.payment.domain.port.AuditEventPort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_BACKOFF = Duration.ofMillis(500);

    private final PaymentPersistencePort paymentPersistencePort;
    private final ExternalServicePort externalServicePort;
    private final AuditEventPort auditEventPort;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public Mono<PaymentResponse> processPayment(PaymentRequest request) {
        log.info("Iniciando procesamiento de pago con idempotencyKey: {}", request.idempotencyKey());
        
        return validateAndPersistPayment(request)
                .flatMap(this::verifyFundsWithExternalService)
                .flatMap(this::updatePaymentStatus)
                .flatMap(this::emitAuditEvent)
                .onErrorResume(handleError(request));
    }

    private Mono<PaymentRequest> validateAndPersistPayment(PaymentRequest request) {
        return Mono.fromCallable(() -> {
            request.validate();
            return request;
        })
        .flatMap(validRequest -> paymentPersistencePort.findByIdempotencyKey(validRequest.idempotencyKey())
                .flatMap(existing -> Mono.error(
                        new DuplicatePaymentException(
                                request.idempotencyKey(),
                                existing.id(),
                                DuplicatePaymentException.createMessage(request.idempotencyKey(), existing.id())
                        )
                ))
                .switchIfEmpty(Mono.defer(() -> {
                    PaymentRequest pendingRequest = request.withStatus(PaymentStatus.PENDING);
                    return paymentPersistencePort.save(pendingRequest);
                }))
        );
    }

    private Mono<PaymentRequest> verifyFundsWithExternalService(PaymentRequest request) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("externalService");
        
        return externalServicePort.verifyFunds(request.accountId(), request.amount())
                .timeout(Duration.ofSeconds(5))
                .transformDeferred(mono -> CircuitBreakerOperator.of(circuitBreaker, mono))
                .flatMap(response -> {
                    if (!response.fundsAvailable()) {
                        return Mono.error(new ExternalServiceException("Fondos insuficientes"));
                    }
                    return Mono.just(request);
                });
    }

    private Mono<PaymentRequest> updatePaymentStatus(PaymentRequest request) {
        PaymentRequest approvedRequest = request.withStatus(PaymentStatus.APPROVED);
        return paymentPersistencePort.save(approvedRequest)
                .doOnSuccess(saved -> log.info("Pago aprobado con ID: {}", saved.id()));
    }

    private Mono<PaymentResponse> emitAuditEvent(PaymentRequest request) {
        return auditEventPort.emitPaymentProcessedEvent(request)
                .thenReturn(PaymentResponse.success(request.id()))
                .doOnSuccess(response -> log.info("Evento de auditoría emitido para pago: {}", request.id()));
    }

    private Function<Throwable, Mono<? extends PaymentResponse>> handleError(PaymentRequest request) {
        return throwable -> {
            log.error("Error procesando pago con idempotencyKey: {} - Tipo: {}", 
                    request.idempotencyKey(), throwable.getClass().getSimpleName(), throwable);
            
            if (throwable instanceof DuplicatePaymentException dupEx) {
                return paymentPersistencePort.findByIdempotencyKey(dupEx.getIdempotencyKey())
                        .map(existing -> PaymentResponse.duplicate(existing.id()));
            }
            
            return persistErrorAndReturnResponse(request, throwable);
        };
    }

    private Mono<PaymentResponse> persistErrorAndReturnResponse(PaymentRequest request, Throwable throwable) {
        PaymentRequest failedRequest = request.withStatus(PaymentStatus.FAILED);
        
        return paymentPersistencePort.save(failedRequest)
                .flatMap(saved -> auditEventPort.emitPaymentFailedEvent(saved, throwable.getMessage())
                        .thenReturn(PaymentResponse.failure(saved.id(), throwable.getMessage())));
    }

    public Mono<PaymentResponse> retryPayment(String paymentId) {
        return paymentPersistencePort.findById(paymentId)
                .filter(p -> p.status() == PaymentStatus.FAILED)
                .flatMap(this::processPayment)
                .switchIfEmpty(Mono.error(new IllegalStateException("Pago no encontrado o no está en estado FAILED")));
    }
}