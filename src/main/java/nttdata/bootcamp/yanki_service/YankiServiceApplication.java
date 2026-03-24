package nttdata.bootcamp.yanki_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Bootstrap for the Yanki wallet microservice (outbox publisher scheduling enabled).
 */
@SpringBootApplication
@EnableScheduling
public class YankiServiceApplication {
    /**
     * @param args standard Spring Boot arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(YankiServiceApplication.class, args);
    }
}
