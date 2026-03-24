package nttdata.bootcamp.yanki_service.Controller;

import com.bank.yanki.api.YankiApi;
import com.bank.yanki.model.CreateWalletRequest;
import com.bank.yanki.model.LinkDebitCardRequest;
import com.bank.yanki.model.TopupRequest;
import com.bank.yanki.model.WalletResponse;
import com.bank.yanki.model.WithdrawalRequest;
import com.bank.yanki.model.YankiPaymentRequest;
import com.bank.yanki.model.YankiPaymentResponse;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import nttdata.bootcamp.yanki_service.Service.YankiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Reactive REST controller for Yanki mobile wallet operations (OpenAPI implementation).
 */
@RestController
@RequiredArgsConstructor
public class YankiController implements YankiApi {

    private final YankiService yankiService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<List<WalletResponse>>> listWallets() {
        return yankiService.listWallets()
                .map(ResponseEntity::ok);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> createWallet(CreateWalletRequest createWalletRequest) {
        return yankiService.createWallet(createWalletRequest)
                .map(wallet -> ResponseEntity.status(HttpStatus.CREATED).body(wallet));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> getWalletById(String id) {
        return yankiService.getWalletById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> getWalletByPhone(String phoneNumber) {
        return yankiService.getWalletByPhone(phoneNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> linkDebitCard(String walletId, LinkDebitCardRequest linkDebitCardRequest) {
        return yankiService.linkDebitCard(walletId, linkDebitCardRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> unlinkDebitCard(String walletId) {
        return yankiService.unlinkDebitCard(walletId)
                .map(ResponseEntity::ok);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> topup(TopupRequest topupRequest) {
        return yankiService.topup(topupRequest)
                .map(wallet -> ResponseEntity.status(HttpStatus.CREATED).body(wallet));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<WalletResponse>> withdraw(WithdrawalRequest withdrawalRequest) {
        return yankiService.withdraw(withdrawalRequest)
                .map(wallet -> ResponseEntity.status(HttpStatus.CREATED).body(wallet));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<ResponseEntity<YankiPaymentResponse>> createPayment(YankiPaymentRequest yankiPaymentRequest) {
        return yankiService.createPayment(yankiPaymentRequest)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp));
    }
}
