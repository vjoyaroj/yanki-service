package nttdata.bootcamp.yanki_service.Mapper;

import com.bank.yanki.model.CreateWalletRequest;
import com.bank.yanki.model.DocumentType;
import com.bank.yanki.model.WalletResponse;
import com.bank.yanki.model.WalletStatus;
import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Maps between OpenAPI wallet models and {@link YankiWalletDocument}.
 */
@Component
public class YankiMapper {

    /**
     * Builds a new wallet document from a create request (zero balance, ACTIVE).
     */
    public YankiWalletDocument toDocument(CreateWalletRequest request) {
        YankiWalletDocument doc = new YankiWalletDocument();
        doc.setId(UUID.randomUUID().toString());
        doc.setDocumentType(request.getDocumentType().getValue());
        doc.setDocumentNumber(request.getDocumentNumber());
        doc.setPhoneNumber(request.getPhoneNumber());
        doc.setImei(request.getImei());
        doc.setEmail(request.getEmail());
        doc.setStatus("ACTIVE");
        doc.setBalance(0.0);
        doc.setLinkedDebitCardId(null);
        doc.setCreatedAt(Instant.now());
        return doc;
    }

    /**
     * Converts a persisted wallet to API response.
     */
    public WalletResponse toResponse(YankiWalletDocument doc) {
        WalletResponse response = new WalletResponse();
        response.setId(doc.getId());
        if (doc.getDocumentType() != null) {
            response.setDocumentType(DocumentType.fromValue(doc.getDocumentType()));
        }
        response.setDocumentNumber(doc.getDocumentNumber());
        response.setPhoneNumber(doc.getPhoneNumber());
        response.setImei(doc.getImei());
        response.setEmail(doc.getEmail());
        if (doc.getStatus() != null) {
            response.setStatus(WalletStatus.fromValue(doc.getStatus()));
        }
        response.setBalance(doc.getBalance());
        if (doc.getLinkedDebitCardId() != null) {
            response.linkedDebitCardId(doc.getLinkedDebitCardId());
        }
        if (doc.getCreatedAt() != null) {
            response.setCreatedAt(OffsetDateTime.ofInstant(doc.getCreatedAt(), ZoneOffset.UTC));
        }
        return response;
    }
}
