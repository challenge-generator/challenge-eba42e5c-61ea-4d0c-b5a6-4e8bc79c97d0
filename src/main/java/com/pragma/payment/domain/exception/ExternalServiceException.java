package com.pragma.payment.domain.exception;

import java.io.Serial;

public class ExternalServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String serviceName;
    private final String errorCode;
    private final boolean retryable;

    public ExternalServiceException(String serviceName, String errorCode, String message) {
        super(message);
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    public ExternalServiceException(String serviceName, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    private static boolean determineRetryable(String errorCode) {
        if (errorCode == null) {
            return false;
        }
        return errorCode.startsWith("ERR_TIMEOUT") || 
               errorCode.startsWith("ERR_CONNECTION") ||
               errorCode.equals("ERR_SERVICE_UNAVAILABLE");
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static String createMessage(String serviceName, String errorCode, String details) {
        return String.format("External service error [%s]: %s - %s", serviceName, errorCode, details);
    }
}