package nttdata.bootcamp.yanki_service.Rules;

import nttdata.bootcamp.yanki_service.Client.DebitCardClient;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Resolves the primary bank account id linked to a debit card for funding flows.
 */
@Component
@RequiredArgsConstructor
public class DebitCardPrimaryAccountResolver {

    private final DebitCardClient debitCardClient;

    public Mono<String> resolve(String debitCardId) {
        return debitCardClient.getDebitCardById(debitCardId)
                .switchIfEmpty(Mono.error(new RuntimeException("Debit card not found")))
                .flatMap(card -> {
                    if (card.getLinkedAccounts() == null || card.getLinkedAccounts().isEmpty()) {
                        return Mono.error(new BusinessRuleException("Debit card has no linked accounts"));
                    }
                    String accountId = card.getLinkedAccounts().stream()
                            .filter(l -> Boolean.TRUE.equals(l.getIsPrimary()))
                            .map(l -> l.getAccountId())
                            .findFirst()
                            .orElse(card.getLinkedAccounts().get(0).getAccountId());
                    if (accountId == null || accountId.isBlank()) {
                        return Mono.error(new BusinessRuleException("Primary account is invalid"));
                    }
                    return Mono.just(accountId);
                });
    }
}
