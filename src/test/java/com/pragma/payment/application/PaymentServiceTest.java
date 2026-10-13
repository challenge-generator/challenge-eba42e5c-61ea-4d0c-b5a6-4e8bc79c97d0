package com.pragma.payment.application;


import com.pragma.payment.domain.model.PaymentStatus;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentResponse;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.AuditEventPort;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentPersistencePort paymentPersistencePort;

    @Mock
    private ExternalServicePort externalServicePort;

    @Mock
    private AuditEventPort auditEventPort;

    @Mock
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Mock
    private CircuitBreaker circuitBreaker;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
        paymentService = new PaymentService(
                paymentPersistencePort,
                externalServicePort,
                auditEventPort,
                circuitBreakerRegistry
        );
    }

    @Test
    void processPayment_success_returnsApprovedResponse() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                1000.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.just("approval-" + System.currentTimeMillis()));
        when(auditEventPort.publish(any(PaymentRequest.class)))
                .thenReturn(Mono.empty());

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(response -> "approval-".equals(response.paymentId().substring(0, 8)))
                .verifyComplete();
    }

    @Test
    void processPayment_duplicateIdempotencyKey_throwsDuplicatePaymentException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest existingRequest = new PaymentRequest(
                "txn-existing",
                "MOBILE",
                500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.APPROVED,
                null,
                "existing-payment-id",
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(existingRequest));

        // When
        PaymentRequest newRequest = new PaymentRequest(
                "txn-new",
                "MOBILE",
                500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );
        Mono<PaymentResponse> result = paymentService.processPayment(newRequest);

        // Then
        StepVerifier.create(result)
                .expectError(DuplicatePaymentException.class)
                .verify();
    }

    @Test
    void processPayment_externalServiceFailure_throwsExternalServiceException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "API",
                2500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new ExternalServiceException("Fondos insuficientes")));

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(ExternalServiceException.class)
                .verify();
    }

    @Test
    void processPayment_networkError_retriesAndEventuallyFails() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                100.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new NetworkException("Connection timeout")));

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(NetworkException.class)
                .verify();
    }

    @Test
    void processPayment_invalidAmount_throwsIllegalArgumentException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                -100.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}