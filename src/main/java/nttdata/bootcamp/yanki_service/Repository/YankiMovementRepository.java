package nttdata.bootcamp.yanki_service.Repository;

import nttdata.bootcamp.yanki_service.Entity.YankiMovementDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Wallet movement history including idempotent P2P keys.
 */
public interface YankiMovementRepository extends ReactiveMongoRepository<YankiMovementDocument, String> {
    /**
     * @param walletId wallet id
     * @param idempotencyKey client-supplied idempotency key
     * @return existing movement if any
     */
    Mono<YankiMovementDocument> findByWalletIdAndIdempotencyKey(String walletId, String idempotencyKey);
}
