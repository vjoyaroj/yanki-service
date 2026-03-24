package nttdata.bootcamp.yanki_service.Service;

import com.bank.yanki.model.LinkDebitCardRequest;
import com.bank.yanki.model.TopupRequest;
import com.bank.yanki.model.WalletResponse;
import com.bank.yanki.model.WithdrawalRequest;
import com.bank.yanki.model.CreateWalletRequest;
import com.bank.yanki.model.YankiPaymentRequest;
import com.bank.yanki.model.YankiPaymentResponse;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

/**
 * Business operations for Yanki wallets: funding via linked debit card account, P2P and Kafka outbox.
 */
public interface YankiService {
    /** Lists all wallets. */
    Single<List<WalletResponse>> listWallets();

    /** Creates a wallet and emits outbox event. */
    Single<WalletResponse> createWallet(CreateWalletRequest request);
    /** Loads wallet by id. */
    Maybe<WalletResponse> getWalletById(String id);
    /** Loads wallet by phone (may use Redis cache). */
    Maybe<WalletResponse> getWalletByPhone(String phoneNumber);

    /** Links a debit card after validation. */
    Single<WalletResponse> linkDebitCard(String walletId, LinkDebitCardRequest request);
    /** Unlinks debit card. */
    Single<WalletResponse> unlinkDebitCard(String walletId);

    /** Top-up from primary linked account. */
    Single<WalletResponse> topup(TopupRequest request);
    /** Withdraw to primary linked account. */
    Single<WalletResponse> withdraw(WithdrawalRequest request);

    /** P2P payment between wallets by phone. */
    Single<YankiPaymentResponse> createPayment(YankiPaymentRequest request);
}
