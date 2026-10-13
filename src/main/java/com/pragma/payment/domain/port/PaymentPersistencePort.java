package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPersistencePort {

    PaymentRequest save(PaymentRequest paymentRequest);

    Optional<PaymentRequest> findById(UUID paymentId);

    Optional<PaymentRequest> findByIdempotencyKey(String idempotencyKey);

    PaymentRequest updateStatus(UUID paymentId, PaymentStatus newStatus);

    List<PaymentRequest> findByStatus(PaymentStatus status);

    List<PaymentRequest> findByChannel(String channel);

    List<PaymentRequest> findByChannelAndStatus(String channel, PaymentStatus status);

    boolean existsByIdempotencyKey(String idempotencyKey);

    long countByStatus(PaymentStatus status);

    List<PaymentRequest> findPendingPaymentsOlderThan(long timestampMillis);
}