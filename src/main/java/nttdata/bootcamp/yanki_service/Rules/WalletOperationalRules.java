package nttdata.bootcamp.yanki_service.Rules;

import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Wallet state checks (active, linked card, positive amounts, balances).
 */
@Component
public class WalletOperationalRules {

    public Mono<Void> requireActiveWallet(YankiWalletDocument wallet) {
        if (!"ACTIVE".equalsIgnoreCase(wallet.getStatus())) {
            return Mono.error(new BusinessRuleException("Wallet is not active"));
        }
        return Mono.empty();
    }

    public Mono<Void> requireLinkedDebitCard(YankiWalletDocument wallet) {
        if (wallet.getLinkedDebitCardId() == null || wallet.getLinkedDebitCardId().isBlank()) {
            return Mono.error(new BusinessRuleException("Wallet has no linked debit card"));
        }
        return Mono.empty();
    }

    public Mono<Void> requirePositiveAmount(Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new BusinessRuleException("Amount must be greater than 0"));
        }
        return Mono.empty();
    }

    public Mono<Void> requireSufficientWalletBalance(YankiWalletDocument wallet, double amount) {
        double currentBalance = wallet.getBalance() != null ? wallet.getBalance() : 0.0;
        if (currentBalance < amount) {
            return Mono.error(new BusinessRuleException("Insufficient wallet balance"));
        }
        return Mono.empty();
    }
}
