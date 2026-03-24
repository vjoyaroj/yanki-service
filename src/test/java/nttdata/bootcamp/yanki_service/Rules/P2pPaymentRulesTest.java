package nttdata.bootcamp.yanki_service.Rules;

import com.bank.yanki.model.YankiPaymentRequest;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link P2pPaymentRules}.
 */
class P2pPaymentRulesTest {

    private final P2pPaymentRules rules = new P2pPaymentRules();

    @Test
    void validatePaymentRequest_rejectsSameSenderAndReceiver() {
        YankiPaymentRequest req = new YankiPaymentRequest();
        req.setSenderPhoneNumber("+51999999999");
        req.setReceiverPhoneNumber("+51999999999");
        req.setAmount(10d);
        assertThrows(BusinessRuleException.class, () -> rules.validatePaymentRequest(req).block());
    }

    @Test
    void validatePaymentRequest_rejectsNonPositiveAmount() {
        YankiPaymentRequest req = new YankiPaymentRequest();
        req.setSenderPhoneNumber("+511");
        req.setReceiverPhoneNumber("+512");
        req.setAmount(0d);
        assertThrows(BusinessRuleException.class, () -> rules.validatePaymentRequest(req).block());
    }
}
