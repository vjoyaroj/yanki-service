package nttdata.bootcamp.yanki_service.Client;

import nttdata.bootcamp.yanki_service.Dto.DebitCardDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Reactive client for debit-cards-service (link validation and primary account resolution).
 */
@Component
public class DebitCardClient {

    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    /**
     * @param builder WebClient builder
     * @param debitCardServiceUrl base URL
     * @param circuitBreakerFactory breaker factory
     */
    public DebitCardClient(WebClient.Builder builder,
                           @Value("${debitcard.service.url:http://localhost:8085/api/v1}") String debitCardServiceUrl,
                           ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.webClient = builder.baseUrl(debitCardServiceUrl).build();
        this.circuitBreaker = circuitBreakerFactory.create("debitCardServiceCb");
    }

    /**
     * @param id debit card id
     * @return card with linked accounts
     */
    public Mono<DebitCardDto> getDebitCardById(String id) {
        return circuitBreaker.run(
                webClient.get()
                        .uri("/debit-cards/{id}", id)
                        .retrieve()
                        .bodyToMono(DebitCardDto.class),
                throwable -> Mono.error(new RuntimeException(
                        "Fallback: Debit Card Service is currently unavailable. Details: " + throwable.getMessage()))
        );
    }
}
