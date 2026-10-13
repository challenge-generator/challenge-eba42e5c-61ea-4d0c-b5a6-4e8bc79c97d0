package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import reactor.core.publisher.Mono;

public interface ExternalServicePort {

    Mono<Boolean> verifyFundsAvailability(PaymentRequest paymentRequest);

    Mono<Boolean> validateClientAccount(String clientId);

    Mono<String> getServiceHealthStatus();
}