package com.pragma.payment.infrastructure.controller;

import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Mono<ResponseEntity<PaymentResponse>> processPayment(
            @Valid @RequestBody PaymentRequestDTO request) {
        
        log.info("Received payment request - Channel: {}, TransactionId: {}, Amount: {}",
            request.channel(), request.transactionId(), request.amount());
        
        PaymentRequest paymentRequest = new PaymentRequest(
            request.transactionId(),
            request.channel(),
            request.amount(),
            request.idempotencyKey()
        );
        
        return paymentService.processPayment(paymentRequest)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
            .onErrorResume(DuplicatePaymentException.class, ex -> {
                log.warn("Duplicate payment detected - IdempotencyKey: {}, ExistingPaymentId: {}",
                    ex.getIdempotencyKey(), ex.getExistingPaymentId());
                return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(PaymentResponse.duplicate(ex.getExistingPaymentId())));
            })
            .onErrorResume(Exception.class, ex -> {
                log.error("Payment processing failed - TransactionId: {}, Error: {}",
                    request.transactionId(), ex.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PaymentResponse.failure(null, ex.getMessage())));
            });
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }

    public record PaymentRequestDTO(
        @NotBlank(message = "Transaction ID is required")
        String transactionId,
        
        @NotBlank(message = "Channel is required")
        String channel,
        
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        Double amount,
        
        @NotBlank(message = "Idempotency key is required")
        String idempotencyKey
    ) {}
}