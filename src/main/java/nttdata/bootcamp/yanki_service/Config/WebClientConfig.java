package nttdata.bootcamp.yanki_service.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Shared {@link WebClient.Builder} for account and debit-card clients.
 */
@Configuration
public class WebClientConfig {

    /**
     * @return reusable WebClient builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
