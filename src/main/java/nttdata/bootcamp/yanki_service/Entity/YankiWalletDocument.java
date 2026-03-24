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
 * MongoDB aggregate for a Yanki mobile wallet (balance and optional linked debit card).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "yanki_wallets")
public class YankiWalletDocument {
    @Id
    private String id;

    private String documentType;
    private String documentNumber;

    @Indexed(unique = true)
    private String phoneNumber;

    private String imei;
    private String email;

    private String status;

    private Double balance;

    private String linkedDebitCardId;

    private Instant createdAt;
}
