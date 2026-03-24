package nttdata.bootcamp.yanki_service.Repository;

import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Outbox table for reliable Kafka publishing (pending events ordered by time).
 */
public interface OutboxEventRepository extends ReactiveMongoRepository<OutboxEventDocument, String> {
    /**
     * Batch fetch for the publisher job.
     */
    Flux<OutboxEventDocument> findTop100ByStatusOrderByCreatedAtAsc(String status);
}
