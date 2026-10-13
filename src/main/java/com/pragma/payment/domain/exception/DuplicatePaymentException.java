package com.pragma.payment.domain.exception;

import java.io.Serial;

/**
 * Excepción lanzada cuando se detecta un intento de procesar un pago duplicado.
 * Esto ocurre cuando se recibe una solicitud con la misma clave de idempotencia
 * para la cual ya existe un pago procesado en el sistema.
 */
public class DuplicatePaymentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String idempotencyKey;
    private final String existingPaymentId;

    /**
     * Constructor para la excepción de pago duplicado.
     * @param idempotencyKey la clave de idempotencia duplicada
     * @param existingPaymentId el ID del pago existente que tiene la misma clave
     * @param message mensaje descriptivo del error
     */
    public DuplicatePaymentException(String idempotencyKey, String existingPaymentId, String message) {
        super(message);
        this.idempotencyKey = idempotencyKey;
        this.existingPaymentId = existingPaymentId;
    }

    /**
     * Obtiene la clave de idempotencia que causó el conflicto.
     * @return la clave de idempotencia
     */
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    /**
     * Obtiene el ID del pago existente que tiene la misma clave de idempotencia.
     * @return el ID del pago existente
     */
    public String getExistingPaymentId() {
        return existingPaymentId;
    }

    /**
     * Crea un mensaje detallado para esta excepción.
     * @param idempotencyKey la clave de idempotencia duplicada
     * @param existingPaymentId el ID del pago existente
     * @return mensaje detallado del error
     */
    public static String createMessage(String idempotencyKey, String existingPaymentId) {
        return String.format(
            "Pago con clave de idempotencia '%s' ya existe como pago '%s'",
            idempotencyKey,
            existingPaymentId
        );
    }
}