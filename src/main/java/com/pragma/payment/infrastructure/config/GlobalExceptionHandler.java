package com.pragma.payment.infrastructure.config;

import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.model.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePaymentException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleDuplicatePayment(DuplicatePaymentException ex) {
        log.warn("Pago duplicado detectado - idempotencyKey: {}, paymentId existente: {}",
                ex.getIdempotencyKey(), ex.getExistingPaymentId());

        PaymentResponse response = PaymentResponse.duplicate(ex.getExistingPaymentId());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }

    @ExceptionHandler(ExternalServiceException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleExternalServiceException(ExternalServiceException ex) {
        log.error("Error del servicio externo: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error en el servicio externo: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response));
    }

    @ExceptionHandler(NetworkException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleNetworkException(NetworkException ex) {
        log.error("Error de red: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error de conexión: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento inválido: {}", ex.getMessage());

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Solicitud inválida: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Tipo de argumento inválido: {}", ex.getMessage());

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Tipo de parámetro inválido"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<PaymentResponse>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error interno del servidor"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
}