package nttdata.bootcamp.yanki_service.Events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Builds {@link OutboxEventDocument} rows with JSON payloads for wallet and payment topics.
 */
@Component
@RequiredArgsConstructor
public class YankiEventFactory {
    private final ObjectMapper objectMapper;

    @Value("${yanki.kafka.topics.wallet:bank.yanki.wallet.v1}")
    private String walletTopic;

    @Value("${yanki.kafka.topics.payment:bank.yanki.payment.v1}")
    private String paymentTopic;

    public OutboxEventDocument walletEvent(YankiEventType type, YankiWalletDocument wallet) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("eventType", type.name());
        payload.put("walletId", wallet.getId());
        payload.put("phoneNumber", wallet.getPhoneNumber());
        payload.put("status", wallet.getStatus());
        payload.put("balance", wallet.getBalance());
        payload.put("linkedDebitCardId", wallet.getLinkedDebitCardId());
        payload.put("occurredAt", Instant.now().toString());
        return build(walletTopic, type, "WALLET", wallet.getId(), wallet.getId(), payload);
    }

    public OutboxEventDocument paymentCreated(
            String paymentId,
            String senderWalletId,
            String receiverWalletId,
            String senderPhone,
            String receiverPhone,
            Double amount,
            String description,
            String idempotencyKey
    ) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("eventType", YankiEventType.PAYMENT_CREATED.name());
        payload.put("paymentId", paymentId);
        payload.put("senderWalletId", senderWalletId);
        payload.put("receiverWalletId", receiverWalletId);
        payload.put("senderPhoneNumber", senderPhone);
        payload.put("receiverPhoneNumber", receiverPhone);
        payload.put("amount", amount);
        payload.put("description", description);
        payload.put("idempotencyKey", idempotencyKey);
        payload.put("occurredAt", Instant.now().toString());
        return build(paymentTopic, YankiEventType.PAYMENT_CREATED, "PAYMENT", paymentId, senderWalletId, payload);
    }

    private OutboxEventDocument build(
            String topic,
            YankiEventType eventType,
            String aggregateType,
            String aggregateId,
            String partitionKey,
            Map<String, Object> payload
    ) {
        String eventId = UUID.randomUUID().toString();
        payload.put("eventId", eventId);
        payload.put("eventType", eventType.name());
        payload.put("aggregateType", aggregateType);
        payload.put("aggregateId", aggregateId);
        return OutboxEventDocument.builder()
                .id(UUID.randomUUID().toString())
                .eventId(eventId)
                .topic(topic)
                .eventType(eventType.name())
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .partitionKey(partitionKey)
                .payload(toJson(payload))
                .status(OutboxEventDocument.STATUS_PENDING)
                .retryCount(0)
                .createdAt(Instant.now())
                .build();
    }

    private String toJson(Map<String, Object> payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize outbox payload", e);
        }
    }
}
