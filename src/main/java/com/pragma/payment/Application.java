package com.pragma.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Hooks;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.beans.factory.annotation.Autowired;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.AuditEventPort;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.infrastructure.adapter.PaymentJpaAdapter;
import com.pragma.payment.infrastructure.adapter.ExternalServiceAdapter;
import com.pragma.payment.infrastructure.adapter.AuditEventAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.function.Function;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = "com.pragma.payment.infrastructure.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.pragma.payment"})
public class Application {

    @Autowired
    private PaymentPersistencePort paymentPersistencePort;

    @Autowired
    private ExternalServicePort externalServicePort;

    @Autowired
    private AuditEventPort auditEventPort;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("externalService");
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> {
                    if (event.getStateTransition() == CircuitBreaker.StateTransition.FORCED_OPEN_TO_CLOSED) {
                        System.out.println("Circuit breaker recuperado, volviendo a operar");
                    }
                });
    }

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(paymentPersistencePort, externalServicePort, auditEventPort);
    }

    @Bean
    public Function<Throwable, Throwable> retryOnSpecificExceptions() {
        return throwable -> {
            if (throwable instanceof ExternalServiceException ||
                throwable instanceof NetworkException) {
                return throwable;
            }
            return new RuntimeException("Error no recuperable", throwable);
        };
    }

    @Bean
    public PaymentJpaAdapter paymentJpaAdapter() {
        return new PaymentJpaAdapter();
    }

    @Bean
    public ExternalServiceAdapter externalServiceAdapter() {
        return new ExternalServiceAdapter();
    }

    @Bean
    public AuditEventAdapter auditEventAdapter() {
        return new AuditEventAdapter();
    }
}

import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

class PaymentServiceInitializer {

    public static Mono<Void> initializePaymentFlow(PaymentService paymentService) {
        return Mono.fromRunnable(() -> {
            System.out.println("Inicializando flujo de pagos con manejo reactivo");
        }).then(Mono.defer(() -> paymentService.processPayment(
            new PaymentRequest("123e4567-e89b-12d3-a456-426614174000",
                              "web",
                              100.0,
                              "idempotency-key-123")))
        ).retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
            .filter(throwable -> throwable instanceof ExternalServiceException ||
                                throwable instanceof NetworkException)
            .doAfterRetry(signal -> System.out.println("Reintentando pago tras error: " + signal.failure().getMessage()))
        ).onErrorResume(ex -> {
            System.err.println("Error irrecuperable en el procesamiento del pago: " + ex.getMessage());
            return Mono.empty();
        });
    }

    record PaymentRequest(String transactionId, String channel, double amount, String idempotencyKey) {}
}