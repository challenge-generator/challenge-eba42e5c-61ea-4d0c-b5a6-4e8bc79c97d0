package com.pragma.payment.domain.model;


import com.pragma.payment.PaymentRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo de dominio que representa la respuesta de una solicitud de pago.
 * Contiene información sobre el resultado del procesamiento del pago.
 */
public record PaymentResponse(
    String paymentId,
    PaymentRequest.PaymentStatus status,
    LocalDateTime timestamp,
    String message
) {
    /**
     * Constructor compacto para crear una respuesta de pago.
     */
    public PaymentResponse {
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(status, "status cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");

        if (message == null) {
            message = "";
        }
    }

    /**
     * Crea una respuesta exitosa para un pago completado.
     * @param paymentId el ID del pago generado
     * @return una instancia de PaymentResponse con estado COMPLETED
     */
    public static PaymentResponse success(String paymentId) {
        return new PaymentResponse(
            paymentId,
            PaymentRequest.PaymentStatus.COMPLETED,
            LocalDateTime.now(),
            "Pago procesado exitosamente"
        );
    }

    /**
     * Crea una respuesta para un pago fallido.
     * @param paymentId el ID del pago (puede ser null si no se persistió)
     * @param errorMessage mensaje de error descriptivo
     * @return una instancia de PaymentResponse con estado FAILED
     */
    public static PaymentResponse failure(String paymentId, String errorMessage) {
        return new PaymentResponse(
            paymentId != null ? paymentId : "",
            PaymentRequest.PaymentStatus.FAILED,
            LocalDateTime.now(),
            errorMessage
        );
    }

    /**
     * Crea una respuesta para un pago duplicado.
     * @param paymentId el ID del pago original que ya fue procesado
     * @return una instancia de PaymentResponse con estado DUPLICATE
     */
    public static PaymentResponse duplicate(String paymentId) {
        return new PaymentResponse(
            paymentId,
            PaymentRequest.PaymentStatus.DUPLICATE,
            LocalDateTime.now(),
            "Pago duplicado detectado"
        );
    }
}