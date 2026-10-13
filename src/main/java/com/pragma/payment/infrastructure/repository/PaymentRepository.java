package com.pragma.payment.infrastructure.repository;

import com.pragma.payment.infrastructure.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.idempotencyKey = :idempotencyKey")
    Mono<Optional<PaymentEntity>> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Query("SELECT p FROM PaymentEntity p WHERE p.transactionId = :transactionId")
    Mono<Optional<PaymentEntity>> findByTransactionId(@Param("transactionId") String transactionId);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status")
    Mono<java.util.List<PaymentEntity>> findByStatus(@Param("status") String status);

    @Query("SELECT p FROM PaymentEntity p WHERE p.clientId = :clientId ORDER BY p.createdAt DESC")
    Mono<java.util.List<PaymentEntity>> findByClientIdOrderByCreatedAtDesc(@Param("clientId") String clientId);

    @Query("SELECT COUNT(p) FROM PaymentEntity p WHERE p.status = :status AND p.createdAt >= :since")
    Mono<Long> countByStatusAndCreatedAtSince(@Param("status") String status, @Param("since") java.time.Instant since);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = 'PENDING' AND p.createdAt < :timeout")
    Mono<java.util.List<PaymentEntity>> findTimedOutPendingPayments(@Param("timeout") java.time.Instant timeout);
}