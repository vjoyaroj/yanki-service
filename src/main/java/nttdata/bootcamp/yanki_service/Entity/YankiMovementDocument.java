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
 * Immutable movement record (top-up, withdrawal, P2P) for a wallet.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "yanki_movements")
public class YankiMovementDocument {
    @Id
    private String id;

    private String walletId;

    private String type;

    private Double amount;

    private String counterpartyPhone;

    private String description;

    @Indexed
    private String idempotencyKey;

    private Instant createdAt;
}
