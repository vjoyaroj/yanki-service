package nttdata.bootcamp.yanki_service.Rules;

import nttdata.bootcamp.yanki_service.Dto.DebitCardDto;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Validates a debit card can be linked to a wallet (active, has linked accounts).
 */
@Component
public class DebitCardLinkRules {

    public Mono<Void> validateForLink(DebitCardDto card) {
        if (card.getStatus() != null && !"ACTIVE".equalsIgnoreCase(card.getStatus())) {
            return Mono.error(new BusinessRuleException("Debit card is not active"));
        }
        if (card.getLinkedAccounts() == null || card.getLinkedAccounts().isEmpty()) {
            return Mono.error(new BusinessRuleException("Debit card has no linked accounts"));
        }
        return Mono.empty();
    }
}
