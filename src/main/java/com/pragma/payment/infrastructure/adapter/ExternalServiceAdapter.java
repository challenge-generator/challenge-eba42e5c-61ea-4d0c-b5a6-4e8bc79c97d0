package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.port.ExternalServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j
public class ExternalServiceAdapter implements ExternalServicePort {

    private final WebClient webClient;
    private final String externalServiceUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public ExternalServiceAdapter(
            @Value("${external.service.url:https://api.external-payment-provider.com}") String externalServiceUrl,
            @Value("${external.service.timeout.connection:3000}") int connectionTimeout,
            @Value("${external.service.timeout.read:5000}") int readTimeout) {
        this.externalServiceUrl = externalServiceUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.webClient = WebClient.builder()
                .baseUrl(externalServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Mono<FundsVerificationResponse> verifyFunds(String accountId, double amount) {
        log.info("Verificando fondos para cuenta: {} por monto: {}", accountId, amount);
        
        String requestBody = String.format("{\"accountId\":\"%s\",\"amount\":%.2f}", accountId, amount);
        
        return webClient.post()
                .uri("/v1/accounts/{accountId}/verify-funds", accountId)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(FundsVerificationResponse.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(response -> log.info("Verificación de fondos exitosa para cuenta: {}", accountId))
                .doOnError(error -> log.error("Error verificando fondos para cuenta {}: {}", accountId, error.getMessage()))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(500))
                        .filter(throwable -> throwable instanceof WebClientResponseException.ServiceUnavailable
                                || throwable instanceof WebClientResponseException.GatewayTimeout)
                        .doBeforeRetry(signal -> log.warn("Reintentando verificación de fondos, intento: {}", signal.totalRetries() + 1)))
                .onErrorResume(WebClientResponseException.ServiceUnavailable.class, e -> {
                    log.error("Servicio externo no disponible: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Servicio de verificación de fondos no disponible"));
                })
                .onErrorResume(WebClientResponseException.GatewayTimeout.class, e -> {
                    log.error("Timeout del servicio externo: {}", e.getMessage());
                    return Mono.error(new NetworkException("Timeout al verificar fondos"));
                })
                .onErrorResume(WebClientResponseException.BadRequest.class, e -> {
                    log.error("Solicitud inválida al servicio externo: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Solicitud de verificación inválida"));
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error HTTP del servicio externo: {} - {}", e.getStatusCode(), e.getMessage());
                    return Mono.error(new ExternalServiceException("Error comunicando con servicio de fondos: " + e.getStatusCode()));
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error inesperado al verificar fondos: {}", e.getMessage());
                    return Mono.error(new NetworkException("Error de conexión: " + e.getMessage()));
                });
    }

    @Override
    public Mono<Boolean> checkServiceHealth() {
        return webClient.get()
                .uri("/health")
                .retrieve()
                .bodyToMono(HealthCheckResponse.class)
                .map(response -> "UP".equals(response.status()))
                .onErrorReturn(false);
    }

    public record FundsVerificationResponse(boolean fundsAvailable, String accountId, double availableBalance) {
        public static FundsVerificationResponse insufficientFunds(String accountId) {
            return new FundsVerificationResponse(false, accountId, 0.0);
        }

        public static FundsVerificationResponse sufficientFunds(String accountId, double balance) {
            return new FundsVerificationResponse(true, accountId, balance);
        }
    }

    public record HealthCheckResponse(String status, String version) {}
}