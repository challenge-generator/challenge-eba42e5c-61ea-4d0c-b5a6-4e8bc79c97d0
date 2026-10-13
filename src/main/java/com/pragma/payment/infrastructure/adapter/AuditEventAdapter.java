package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.port.AuditEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventAdapter implements AuditEventPort {

    private static final String EVENT_TYPE_PAYMENT_CREATED = "PAYMENT_CREATED";
    private static final String EVENT_TYPE_PAYMENT_PROCESSED = "PAYMENT_PROCESSED";
    private static final String EVENT_TYPE_PAYMENT_FAILED = "PAYMENT_FAILED";

    @Override
    public Mono<Void> emitPaymentCreated(String paymentId, String transactionId, String channel, double amount) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_CREATED, paymentId, Map.of(
            "transactionId", transactionId,
            "channel", channel,
            "amount", String.valueOf(amount),
            "timestamp", Instant.now().toString()
        ));
    }

    @Override
    public Mono<Void> emitPaymentProcessed(String paymentId, String status, String externalReference) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_PROCESSED, paymentId, Map.of(
            "status", status,
            "externalReference", externalReference != null ? externalReference : "N/A",
            "timestamp", Instant.now().toString()
        ));
    }

    @Override
    public Mono<Void> emitPaymentFailed(String paymentId, String errorCode, String errorMessage) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_FAILED, paymentId, Map.of(
            "errorCode", errorCode,
            "errorMessage", errorMessage,
            "timestamp", Instant.now().toString()
        ));
    }

    private Mono<Void> emitAuditEvent(String eventType, String paymentId, Map<String, String> metadata) {
        String eventId = UUID.randomUUID().toString();
        
        return Mono.fromRunnable(() -> {
            log.info("Audit Event [{}] - Type: {}, PaymentId: {}, Metadata: {}",
                eventId, eventType, paymentId, metadata);
        }).then();
    }
}