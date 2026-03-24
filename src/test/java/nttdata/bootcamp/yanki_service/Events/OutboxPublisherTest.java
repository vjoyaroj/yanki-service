package nttdata.bootcamp.yanki_service.Events;

import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import nttdata.bootcamp.yanki_service.Repository.OutboxEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link OutboxPublisher} publish flow.
 */
@ExtendWith(MockitoExtension.class)
class OutboxPublisherTest {
    @Mock
    private OutboxEventRepository repository;
    @Mock
    private KafkaEventProducer producer;

    private OutboxPublisher publisher;

    @BeforeEach
    void setUp() throws Exception {
        publisher = new OutboxPublisher(repository, producer);
        Field batchSizeField = OutboxPublisher.class.getDeclaredField("batchSize");
        batchSizeField.setAccessible(true);
        batchSizeField.set(publisher, 100);
    }

    @Test
    void publishPending_shouldMarkEventAsPublished() {
        OutboxEventDocument event = OutboxEventDocument.builder()
                .id("ob-1")
                .eventId("e-1")
                .topic("bank.yanki.wallet.v1")
                .partitionKey("w-1")
                .payload("{\"eventId\":\"e-1\"}")
                .status(OutboxEventDocument.STATUS_PENDING)
                .retryCount(0)
                .build();

        when(repository.findTop100ByStatusOrderByCreatedAtAsc(OutboxEventDocument.STATUS_PENDING))
                .thenReturn(Flux.just(event));
        when(producer.publish(event)).thenReturn(Mono.empty());
        when(repository.save(event)).thenReturn(Mono.just(event));

        publisher.publishPending();
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        verify(producer).publish(event);
        verify(repository).save(event);
    }
}
