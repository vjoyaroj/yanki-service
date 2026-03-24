package nttdata.bootcamp.yanki_service.Rules;

import nttdata.bootcamp.yanki_service.Dto.AccountDto;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Bank account checks for top-up source account (active, sufficient balance).
 */
@Component
public class AccountFundingRules {

    public Mono<Void> requireActiveAccount(AccountDto account) {
        if (account.getStatus() != null && !"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            return Mono.error(new BusinessRuleException("Account is not active"));
        }
        return Mono.empty();
    }

    public Mono<Void> requireSufficientBalance(AccountDto account, double amount) {
        if (account.getBalance() == null || account.getBalance() < amount) {
            return Mono.error(new BusinessRuleException("Insufficient balance in account"));
        }
        return Mono.empty();
    }
}
