package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import reactor.core.publisher.Mono;

public interface AuditEventPort {

    Mono<Void> emitPaymentAcceptedEvent(PaymentRequest paymentRequest);

    Mono<Void> emitPaymentRejectedEvent(PaymentRequest paymentRequest, String rejectionReason);

    Mono<Void> emitPaymentStatusChangedEvent(PaymentRequest paymentRequest, PaymentStatus oldStatus, PaymentStatus newStatus);

    Mono<Void> emitPaymentProcessedEvent(PaymentRequest paymentRequest, String externalReference);
}