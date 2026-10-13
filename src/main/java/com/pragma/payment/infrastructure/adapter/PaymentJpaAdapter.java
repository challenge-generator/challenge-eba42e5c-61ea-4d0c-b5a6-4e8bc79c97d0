package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.infrastructure.repository.PaymentRepository;
import com.pragma.payment.infrastructure.repository.entity.PaymentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentJpaAdapter implements PaymentPersistencePort {

    private final PaymentRepository paymentRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentRequest> findByIdempotencyKey(String idempotencyKey) {
        return Mono.fromCallable(() -> paymentRepository.findByIdempotencyKey(idempotencyKey)
                        .map(this::toDomain))
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentRequest> findById(String paymentId) {
        return Mono.fromCallable(() -> paymentRepository.findById(paymentId)
                        .map(this::toDomain))
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    @Transactional
    public Mono<PaymentRequest> save(PaymentRequest paymentRequest) {
        return Mono.fromCallable(() -> {
            PaymentEntity entity = toEntity(paymentRequest);
            PaymentEntity savedEntity = paymentRepository.save(entity);
            entityManager.flush();
            return toDomain(savedEntity);
        });
    }

    @Override
    @Transactional
    public Mono<Void> updateStatus(String paymentId, PaymentStatus status) {
        return Mono.fromRunnable(() -> paymentRepository.findById(paymentId)
                .ifPresent(entity -> {
                    entity.setStatus(status.name());
                    paymentRepository.save(entity);
                    entityManager.flush();
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Boolean> existsByIdempotencyKey(String idempotencyKey) {
        return Mono.fromCallable(() -> paymentRepository.existsByIdempotencyKey(idempotencyKey));
    }

    private PaymentEntity toEntity(PaymentRequest request) {
        PaymentEntity entity = new PaymentRequest();
        entity.setTransactionId(request.transactionId());
        entity.setChannel(request.channel());
        entity.setAmount(request.amount());
        entity.setIdempotencyKey(request.idempotencyKey());
        entity.setAccountId(request.accountId());
        entity.setStatus(request.status() != null ? request.status().name() : PaymentStatus.PENDING.name());
        entity.setCreatedAt(request.createdAt());
        entity.setUpdatedAt(request.updatedAt());
        return entity;
    }

    private PaymentRequest toDomain(PaymentEntity entity) {
        return new PaymentRequest(
                entity.getId(),
                entity.getTransactionId(),
                entity.getChannel(),
                entity.getAmount(),
                entity.getAccountId(),
                entity.getIdempotencyKey(),
                PaymentStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}