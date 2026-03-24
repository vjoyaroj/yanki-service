package nttdata.bootcamp.yanki_service.Events;

/**
 * Domain event types emitted to Kafka (wallet lifecycle and payments).
 */
public enum YankiEventType {
    WALLET_CREATED,
    WALLET_DEBIT_CARD_LINKED,
    WALLET_DEBIT_CARD_UNLINKED,
    WALLET_TOPUPPED,
    WALLET_WITHDRAWN,
    PAYMENT_CREATED
}
