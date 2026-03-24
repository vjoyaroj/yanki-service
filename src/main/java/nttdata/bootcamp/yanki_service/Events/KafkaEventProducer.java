package nttdata.bootcamp.yanki_service.Events;

import lombok.RequiredArgsConstructor;
import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Low-level Kafka send for outbox rows.
 */
@Component
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Mono<Void> publish(OutboxEventDocument event) {
        return Mono.fromFuture(kafkaTemplate.send(event.getTopic(), event.getPartitionKey(), event.getPayload()))
                .then();
    }
}
