package nttdata.bootcamp.yanki_service.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Outbox row: domain event payload to be published to Kafka with retry/status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "outbox_events")
public class OutboxEventDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String eventId;

    private String topic;
    private String eventType;
    private String aggregateType;
    private String aggregateId;
    private String partitionKey;
    private String payload;

    @Indexed
    private String status;

    private Integer retryCount;
    private String lastError;
    private Instant createdAt;
    private Instant publishedAt;

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_PUBLISHED = "PUBLISHED";
}
