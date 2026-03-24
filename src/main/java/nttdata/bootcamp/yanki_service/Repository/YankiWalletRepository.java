package nttdata.bootcamp.yanki_service.Repository;

import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive persistence for {@link YankiWalletDocument}.
 */
public interface YankiWalletRepository extends ReactiveMongoRepository<YankiWalletDocument, String> {
    /** Finds wallet by unique phone number. */
    Mono<YankiWalletDocument> findByPhoneNumber(String phoneNumber);
    /** Whether phone is already registered. */
    Mono<Boolean> existsByPhoneNumber(String phoneNumber);
}
