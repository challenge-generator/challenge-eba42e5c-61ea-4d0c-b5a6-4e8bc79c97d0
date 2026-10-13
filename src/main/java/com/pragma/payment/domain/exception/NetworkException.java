package com.pragma.payment.domain.exception;

import java.io.Serial;
import java.net.URI;

public class NetworkException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String host;
    private final int port;
    private final int httpStatusCode;
    private final long timeoutMillis;
    private final boolean retryable;

    public NetworkException(String host, int port, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    public NetworkException(String host, int port, int httpStatusCode, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = httpStatusCode;
        this.timeoutMillis = 0L;
        this.retryable = isHttpStatusRetryable(httpStatusCode);
    }

    public NetworkException(String host, int port, long timeoutMillis, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = timeoutMillis;
        this.retryable = true;
    }

    public NetworkException(String host, int port, String message, Throwable cause) {
        super(message, cause);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    public NetworkException(URI uri, String message, Throwable cause) {
        super(message, cause);
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    private static boolean isHttpStatusRetryable(int statusCode) {
        return statusCode == 408 || statusCode == 429 || statusCode >= 500;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static String createConnectionMessage(String host, int port) {
        return String.format("Connection failed to %s:%d", host, port);
    }

    public static String createTimeoutMessage(String host, int port, long timeoutMillis) {
        return String.format("Connection timeout to %s:%d after %dms", host, port, timeoutMillis);
    }

    public static String createHttpErrorMessage(String host, int port, int statusCode) {
        return String.format("HTTP error %d from %s:%d", statusCode, host, port);
    }
}