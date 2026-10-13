package com.pragma.payment.infrastructure.repository.entity;


import com.pragma.payment.domain.model.PaymentStatus;
import com.pragma.payment.domain.model.PaymentRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotencyKey", unique = true),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_id", nullable = false, length = 64)
    private String transactionId;

    @Column(name = "channel", nullable = false, length = 32)
    private String channel;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private Double amount;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 128)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private PaymentRequest.PaymentStatus status;

    @Column(name = "error_message", length = 512)
    private String errorMessage;

    @Column(name = "external_service_id", length = 128)
    private String externalServiceId;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public static PaymentEntity fromDomain(com.pragma.payment.domain.model.PaymentRequest request) {
        return PaymentEntity.builder()
                .transactionId(request.transactionId())
                .channel(request.channel())
                .amount(request.amount())
                .idempotencyKey(request.idempotencyKey())
                .status(request.status())
                .retryCount(0)
                .build();
    }

    public com.pragma.payment.domain.model.PaymentRequest toDomain() {
        return new com.pragma.payment.domain.model.PaymentRequest(
                this.transactionId,
                this.channel,
                this.amount,
                this.idempotencyKey,
                this.status,
                this.errorMessage,
                this.externalServiceId,
                this.retryCount
        );
    }
}