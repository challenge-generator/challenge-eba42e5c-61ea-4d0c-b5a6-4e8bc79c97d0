package com.pragma.payment.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo de dominio que representa una solicitud de pago.
 * Contiene la información necesaria para procesar un pago, incluyendo
 * la clave de idempotencia para garantizar que no se procese más de una vez.
 */
public record PaymentRequest(
    @NotBlank(message = "El ID de transacción no puede estar vacío")
    @Size(max = 64, message = "El ID de transacción no puede exceder 64 caracteres")
    String transactionId,

    @NotBlank(message = "El canal no puede estar vacío")
    @Pattern(regexp = "^(web|mobile|api)$", message = "El canal debe ser web, mobile o api")
    String channel,

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    BigDecimal amount,

    @NotBlank(message = "La clave de idempotencia no puede estar vacía")
    @Size(max = 128, message = "La clave de idempotencia no puede exceder 128 caracteres")
    String idempotencyKey,

    @NotNull(message = "El timestamp no puede ser nulo")
    LocalDateTime timestamp,

    PaymentStatus status
) {
    /**
     * Constructor compacto para crear una solicitud de pago con estado inicial PENDING.
     */
    public PaymentRequest {
        Objects.requireNonNull(transactionId, "transactionId cannot be null");
        Objects.requireNonNull(channel, "channel cannot be null");
        Objects.requireNonNull(amount, "amount cannot be null");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");

        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }

    /**
     * Enum que representa los posibles estados de una solicitud de pago.
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    /**
     * Valida que la solicitud de pago cumpla con las reglas de negocio.
     * @throws IllegalArgumentException si alguna validación falla
     */
    public void validate() {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (!channel.matches("^(web|mobile|api)$")) {
            throw new IllegalArgumentException("Canal inválido. Debe ser web, mobile o api");
        }
    }

    /**
     * Crea una copia de la solicitud de pago con un nuevo estado.
     * @param newStatus el nuevo estado
     * @return una nueva instancia de PaymentRequest con el estado actualizado
     */
    public PaymentRequest withStatus(PaymentStatus newStatus) {
        return new PaymentRequest(
            this.transactionId,
            this.channel,
            this.amount,
            this.idempotencyKey,
            this.timestamp,
            newStatus
        );
    }
}