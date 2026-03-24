package nttdata.bootcamp.yanki_service.Rules;

import com.bank.yanki.model.YankiPaymentRequest;
import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Validation rules for peer-to-peer payments between wallets.
 */
@Component
public class P2pPaymentRules {

    public Mono<Void> validatePaymentRequest(YankiPaymentRequest request) {
        if (request.getSenderPhoneNumber().equals(request.getReceiverPhoneNumber())) {
            return Mono.error(new BusinessRuleException("Sender and receiver must be different"));
        }
        if (request.getAmount() == null || request.getAmount() <= 0) {
            return Mono.error(new BusinessRuleException("Amount must be greater than 0"));
        }
        return Mono.empty();
    }

    public Mono<Void> requireBothWalletsActive(YankiWalletDocument sender, YankiWalletDocument receiver) {
        if (!"ACTIVE".equalsIgnoreCase(sender.getStatus())) {
            return Mono.error(new BusinessRuleException("Sender wallet is not active"));
        }
        if (!"ACTIVE".equalsIgnoreCase(receiver.getStatus())) {
            return Mono.error(new BusinessRuleException("Receiver wallet is not active"));
        }
        return Mono.empty();
    }

    public Mono<Void> requireSenderBalance(YankiWalletDocument sender, double amount) {
        double senderBalance = sender.getBalance() != null ? sender.getBalance() : 0.0;
        if (senderBalance < amount) {
            return Mono.error(new BusinessRuleException("Insufficient sender balance"));
        }
        return Mono.empty();
    }
}
