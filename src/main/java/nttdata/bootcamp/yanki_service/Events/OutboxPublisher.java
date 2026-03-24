package nttdata.bootcamp.yanki_service.Events;

import lombok.RequiredArgsConstructor;
import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import nttdata.bootcamp.yanki_service.Repository.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Scheduled job that publishes pending outbox events and updates status.
 */
@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaEventProducer producer;

    @Value("${yanki.outbox.publisher.batch-size:100}")
    private int batchSize;

    @Scheduled(fixedDelayString = "${yanki.outbox.publisher.delay-ms:5000}")
    public void publishPending() {
        Flux<OutboxEventDocument> stream = outboxEventRepository
                .findTop100ByStatusOrderByCreatedAtAsc(OutboxEventDocument.STATUS_PENDING)
                .take(batchSize);
        stream.flatMap(this::publishOne).subscribe();
    }

    private Mono<Void> publishOne(OutboxEventDocument event) {
        return producer.publish(event)
                .then(markPublished(event))
                .onErrorResume(ex -> markFailed(event, ex.getMessage()));
    }

    private Mono<Void> markPublished(OutboxEventDocument event) {
        event.setStatus(OutboxEventDocument.STATUS_PUBLISHED);
        event.setPublishedAt(Instant.now());
        event.setLastError(null);
        return outboxEventRepository.save(event).then();
    }

    private Mono<Void> markFailed(OutboxEventDocument event, String message) {
        event.setRetryCount((event.getRetryCount() == null ? 0 : event.getRetryCount()) + 1);
        event.setLastError(message);
        return outboxEventRepository.save(event).then();
    }
}
