package nttdata.bootcamp.yanki_service.Client;

import nttdata.bootcamp.yanki_service.Dto.AccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Reactive client for accounts-service (balance updates for top-up/withdraw).
 */
@Component
public class AccountClient {

    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    /**
     * @param builder WebClient builder
     * @param accountServiceUrl base URL
     * @param circuitBreakerFactory breaker factory
     */
    public AccountClient(WebClient.Builder builder,
                         @Value("${account.service.url:http://localhost:8082/api/v1}") String accountServiceUrl,
                         ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.webClient = builder.baseUrl(accountServiceUrl).build();
        this.circuitBreaker = circuitBreakerFactory.create("accountServiceCb");
    }

    /**
     * @param id account id
     * @return account DTO
     */
    public Mono<AccountDto> getAccountById(String id) {
        return circuitBreaker.run(
                webClient.get()
                        .uri("/accounts/{id}", id)
                        .retrieve()
                        .bodyToMono(AccountDto.class),
                throwable -> Mono.error(new RuntimeException(
                        "Fallback: Account Service is currently unavailable. Details: " + throwable.getMessage()))
        );
    }

    /**
     * @param id account id
     * @param account full account payload for PUT
     * @return updated account
     */
    public Mono<AccountDto> updateAccount(String id, AccountDto account) {
        return circuitBreaker.run(
                webClient.put()
                        .uri("/accounts/{id}", id)
                        .bodyValue(account)
                        .retrieve()
                        .bodyToMono(AccountDto.class),
                throwable -> Mono.error(new RuntimeException(
                        "Fallback: Account Service is currently unavailable when updating account. Details: " + throwable.getMessage()))
        );
    }
}
