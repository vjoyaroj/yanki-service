package nttdata.bootcamp.yanki_service.Rules;

import nttdata.bootcamp.yanki_service.Entity.YankiMovementDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * Factory for {@link nttdata.bootcamp.yanki_service.Entity.YankiMovementDocument} records (top-up, withdrawal, P2P).
 */
@Component
public class YankiMovementFactory {

    public YankiMovementDocument topup(String walletId, Double amount, String description, Instant createdAt) {
        return YankiMovementDocument.builder()
                .id(UUID.randomUUID().toString())
                .walletId(walletId)
                .type("TOPUP")
                .amount(amount)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    public YankiMovementDocument withdrawal(String walletId, Double amount, String description, Instant createdAt) {
        return YankiMovementDocument.builder()
                .id(UUID.randomUUID().toString())
                .walletId(walletId)
                .type("WITHDRAWAL")
                .amount(amount)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    public YankiMovementDocument p2pOut(String paymentId,
                                        String senderWalletId,
                                        Double amount,
                                        String receiverPhone,
                                        String description,
                                        String idempotencyKey,
                                        Instant createdAt) {
        return YankiMovementDocument.builder()
                .id(paymentId)
                .walletId(senderWalletId)
                .type("P2P_OUT")
                .amount(amount)
                .counterpartyPhone(receiverPhone)
                .description(description)
                .idempotencyKey(idempotencyKey)
                .createdAt(createdAt)
                .build();
    }

    public YankiMovementDocument p2pIn(String receiverWalletId,
                                       Double amount,
                                       String senderPhone,
                                       String description,
                                       Instant createdAt) {
        return YankiMovementDocument.builder()
                .id(UUID.randomUUID().toString())
                .walletId(receiverWalletId)
                .type("P2P_IN")
                .amount(amount)
                .counterpartyPhone(senderPhone)
                .description(description)
                .createdAt(createdAt)
                .build();
    }
}
