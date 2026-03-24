package nttdata.bootcamp.yanki_service.Service.Implement;

import com.bank.yanki.model.CreateWalletRequest;
import com.bank.yanki.model.LinkDebitCardRequest;
import com.bank.yanki.model.TopupRequest;
import com.bank.yanki.model.WalletResponse;
import com.bank.yanki.model.WithdrawalRequest;
import com.bank.yanki.model.YankiPaymentRequest;
import com.bank.yanki.model.YankiPaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import nttdata.bootcamp.yanki_service.Client.AccountClient;
import nttdata.bootcamp.yanki_service.Client.DebitCardClient;
import nttdata.bootcamp.yanki_service.Dto.AccountDto;
import nttdata.bootcamp.yanki_service.Entity.OutboxEventDocument;
import nttdata.bootcamp.yanki_service.Entity.YankiMovementDocument;
import nttdata.bootcamp.yanki_service.Entity.YankiWalletDocument;
import nttdata.bootcamp.yanki_service.Events.YankiEventFactory;
import nttdata.bootcamp.yanki_service.Events.YankiEventType;
import nttdata.bootcamp.yanki_service.Exception.BusinessRuleException;
import nttdata.bootcamp.yanki_service.Mapper.YankiMapper;
import nttdata.bootcamp.yanki_service.Repository.OutboxEventRepository;
import nttdata.bootcamp.yanki_service.Repository.YankiMovementRepository;
import nttdata.bootcamp.yanki_service.Repository.YankiWalletRepository;
import nttdata.bootcamp.yanki_service.Rules.AccountFundingRules;
import nttdata.bootcamp.yanki_service.Rules.DebitCardLinkRules;
import nttdata.bootcamp.yanki_service.Rules.DebitCardPrimaryAccountResolver;
import nttdata.bootcamp.yanki_service.Rules.P2pPaymentRules;
import nttdata.bootcamp.yanki_service.Rules.WalletOperationalRules;
import nttdata.bootcamp.yanki_service.Rules.YankiMovementFactory;
import nttdata.bootcamp.yanki_service.Service.YankiService;
import nttdata.bootcamp.yanki_service.Validation.ValidationSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.adapter.rxjava.RxJava3Adapter;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * Default {@link YankiService}: Mongo reactive transactions, Redis cache by phone, outbox Kafka events.
 */
@Service
@RequiredArgsConstructor
public class YankiServiceImpl implements YankiService {

    private final YankiWalletRepository walletRepository;
    private final YankiMovementRepository movementRepository;
    private final YankiMapper mapper;
    private final ValidationSupport validationSupport;
    private final DebitCardClient debitCardClient;
    private final AccountClient accountClient;
    private final TransactionalOperator tx;
    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final WalletOperationalRules walletOperationalRules;
    private final DebitCardLinkRules debitCardLinkRules;
    private final AccountFundingRules accountFundingRules;
    private final P2pPaymentRules p2pPaymentRules;
    private final DebitCardPrimaryAccountResolver primaryAccountResolver;
    private final YankiMovementFactory movementFactory;
    private final OutboxEventRepository outboxEventRepository;
    private final YankiEventFactory yankiEventFactory;

    @Value("${redis.cache.ttl-seconds:900}")
    private long cacheTtlSeconds;

    /**
     * Removes cached wallet lookup by phone number.
     *
     * @param phoneNumber wallet phone key
     * @return completion or empty if null phone
     */
    private Mono<Void> evictYankiWalletByPhoneCache(String phoneNumber) {
        if (phoneNumber == null) {
            return Mono.empty();
        }
        String key = "yankiWalletByPhone:" + phoneNumber;
        return redisTemplate.delete(key).then();
    }

    @Override
    public Single<List<WalletResponse>> listWallets() {
        return RxJava3Adapter.monoToSingle(
                walletRepository.findAll()
                        .map(mapper::toResponse)
                        .collectList()
        );
    }

    @Override
    public Single<WalletResponse> createWallet(CreateWalletRequest request) {
        return RxJava3Adapter.monoToSingle(
                Mono.fromCallable(() -> validationSupport.validateOrThrow(request))
                        .then(walletRepository.existsByPhoneNumber(request.getPhoneNumber()))
                        .flatMap(exists -> exists
                                ? Mono.error(new BusinessRuleException("Phone number already registered"))
                                : Mono.empty())
                        .then(Mono.fromCallable(() -> mapper.toDocument(request)))
                        .flatMap(doc -> tx.transactional(
                                walletRepository.save(doc)
                                        .flatMap(saved -> outboxEventRepository.save(
                                                yankiEventFactory.walletEvent(
                                                        YankiEventType.WALLET_CREATED, saved))
                                                .thenReturn(saved))
                        ))
                        .map(mapper::toResponse)
        );
    }

    @Override
    public Maybe<WalletResponse> getWalletById(String id) {
        return RxJava3Adapter.monoToMaybe(walletRepository.findById(id))
                .map(mapper::toResponse);
    }

    @Override
    public Maybe<WalletResponse> getWalletByPhone(String phoneNumber) {
        String cacheKey = "yankiWalletByPhone:" + phoneNumber;
        return RxJava3Adapter.monoToMaybe(
                redisTemplate.opsForValue().get(cacheKey)
                        .flatMap(json -> {
                            try {
                                return Mono.just(objectMapper.readValue(json, WalletResponse.class));
                            } catch (JsonProcessingException e) {
                                return Mono.error(new RuntimeException("Failed to deserialize wallet from cache", e));
                            }
                        })
                        .switchIfEmpty(walletRepository.findByPhoneNumber(phoneNumber)
                                .map(mapper::toResponse)
                                .flatMap(dto -> {
                                    try {
                                        String json = objectMapper.writeValueAsString(dto);
                                        return redisTemplate.opsForValue()
                                                .set(cacheKey, json, Duration.ofSeconds(cacheTtlSeconds))
                                                .thenReturn(dto);
                                    } catch (JsonProcessingException e) {
                                        return Mono.error(new RuntimeException("Failed to serialize wallet for cache", e));
                                    }
                                }))
        );
    }

    @Override
    public Single<WalletResponse> linkDebitCard(String walletId, LinkDebitCardRequest request) {
        return RxJava3Adapter.monoToSingle(
                Mono.fromCallable(() -> validationSupport.validateOrThrow(request))
                        .then(walletRepository.findById(walletId)
                                .switchIfEmpty(Mono.error(new RuntimeException("Wallet not found")))
                                .flatMap(wallet -> debitCardClient.getDebitCardById(request.getDebitCardId())
                                        .switchIfEmpty(Mono.error(new RuntimeException("Debit card not found")))
                                        .flatMap(card -> debitCardLinkRules.validateForLink(card)
                                                .thenReturn(card))
                                        .flatMap(card -> {
                                            wallet.setLinkedDebitCardId(card.getId());
                                            return tx.transactional(
                                                    walletRepository.save(wallet)
                                                            .flatMap(saved -> outboxEventRepository.save(
                                                                    yankiEventFactory.walletEvent(
                                                                            YankiEventType.WALLET_DEBIT_CARD_LINKED, saved))
                                                                    .thenReturn(saved))
                                            );
                                        })
                                )
                        )
                        .map(mapper::toResponse)
        ).flatMap(walletResp ->
                RxJava3Adapter.monoToSingle(evictYankiWalletByPhoneCache(walletResp.getPhoneNumber()).thenReturn(walletResp))
        );
    }

    @Override
    public Single<WalletResponse> unlinkDebitCard(String walletId) {
        return RxJava3Adapter.monoToSingle(
                walletRepository.findById(walletId)
                        .switchIfEmpty(Mono.error(new RuntimeException("Wallet not found")))
                        .flatMap(wallet -> {
                            wallet.setLinkedDebitCardId(null);
                            return tx.transactional(
                                    walletRepository.save(wallet)
                                            .flatMap(saved -> outboxEventRepository.save(
                                                    yankiEventFactory.walletEvent(
                                                            YankiEventType.WALLET_DEBIT_CARD_UNLINKED, saved))
                                                    .thenReturn(saved))
                            );
                        })
                        .map(mapper::toResponse)
                        .flatMap(walletResp -> evictYankiWalletByPhoneCache(walletResp.getPhoneNumber()).thenReturn(walletResp))
        );
    }

    @Override
    public Single<WalletResponse> topup(TopupRequest request) {
        return RxJava3Adapter.monoToSingle(
                Mono.fromCallable(() -> validationSupport.validateOrThrow(request))
                        .then(walletRepository.findById(request.getWalletId())
                                .switchIfEmpty(Mono.error(new RuntimeException("Wallet not found")))
                                .flatMap(wallet -> walletOperationalRules.requireActiveWallet(wallet)
                                        .then(walletOperationalRules.requireLinkedDebitCard(wallet))
                                        .then(walletOperationalRules.requirePositiveAmount(request.getAmount()))
                                        .then(primaryAccountResolver.resolve(wallet.getLinkedDebitCardId()))
                                        .flatMap(accountId -> accountClient.getAccountById(accountId)
                                                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                                                .flatMap(account -> accountFundingRules.requireActiveAccount(account)
                                                        .then(accountFundingRules.requireSufficientBalance(
                                                                account, request.getAmount()))
                                                        .then(executeTopup(wallet, account, request))
                                                ))
                                )
                        )
                        .map(mapper::toResponse)
        ).flatMap(walletResp ->
                RxJava3Adapter.monoToSingle(evictYankiWalletByPhoneCache(walletResp.getPhoneNumber()).thenReturn(walletResp))
        );
    }

    /**
     * Debits linked bank account and credits wallet; persists movement and outbox event.
     */
    private Mono<YankiWalletDocument> executeTopup(YankiWalletDocument wallet,
                                                     AccountDto account,
                                                     TopupRequest request) {
        account.setBalance(account.getBalance() - request.getAmount());
        double newBalance = (wallet.getBalance() != null ? wallet.getBalance() : 0.0) + request.getAmount();
        wallet.setBalance(newBalance);
        Instant now = Instant.now();
        YankiMovementDocument movement = movementFactory.topup(
                wallet.getId(), request.getAmount(), request.getDescription(), now);
        return tx.transactional(
                accountClient.updateAccount(account.getId(), account)
                        .then(walletRepository.save(wallet))
                        .flatMap(saved -> movementRepository.save(movement).thenReturn(saved))
                        .flatMap(saved -> outboxEventRepository.save(
                                yankiEventFactory.walletEvent(
                                        YankiEventType.WALLET_TOPUPPED, saved))
                                .thenReturn(saved))
        );
    }

    @Override
    public Single<WalletResponse> withdraw(WithdrawalRequest request) {
        return RxJava3Adapter.monoToSingle(
                Mono.fromCallable(() -> validationSupport.validateOrThrow(request))
                        .then(walletRepository.findById(request.getWalletId())
                                .switchIfEmpty(Mono.error(new RuntimeException("Wallet not found")))
                                .flatMap(wallet -> walletOperationalRules.requireActiveWallet(wallet)
                                        .then(walletOperationalRules.requireLinkedDebitCard(wallet))
                                        .then(walletOperationalRules.requirePositiveAmount(request.getAmount()))
                                        .then(walletOperationalRules.requireSufficientWalletBalance(
                                                wallet, request.getAmount()))
                                        .then(primaryAccountResolver.resolve(wallet.getLinkedDebitCardId()))
                                        .flatMap(accountId -> accountClient.getAccountById(accountId)
                                                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                                                .flatMap(account -> accountFundingRules.requireActiveAccount(account)
                                                        .then(executeWithdraw(wallet, account, request))
                                                ))
                                )
                        )
                        .map(mapper::toResponse)
        ).flatMap(walletResp ->
                RxJava3Adapter.monoToSingle(evictYankiWalletByPhoneCache(walletResp.getPhoneNumber()).thenReturn(walletResp))
        );
    }

    /**
     * Debits wallet and credits linked bank account; persists movement and outbox event.
     */
    private Mono<YankiWalletDocument> executeWithdraw(YankiWalletDocument wallet,
                                                      AccountDto account,
                                                      WithdrawalRequest request) {
        double currentBalance = wallet.getBalance() != null ? wallet.getBalance() : 0.0;
        account.setBalance((account.getBalance() != null ? account.getBalance() : 0.0) + request.getAmount());
        wallet.setBalance(currentBalance - request.getAmount());
        Instant now = Instant.now();
        YankiMovementDocument movement = movementFactory.withdrawal(
                wallet.getId(), request.getAmount(), request.getDescription(), now);
        return tx.transactional(
                accountClient.updateAccount(account.getId(), account)
                        .then(walletRepository.save(wallet))
                        .flatMap(saved -> movementRepository.save(movement).thenReturn(saved))
                        .flatMap(saved -> outboxEventRepository.save(
                                yankiEventFactory.walletEvent(
                                        YankiEventType.WALLET_WITHDRAWN, saved))
                                .thenReturn(saved))
        );
    }

    @Override
    public Single<YankiPaymentResponse> createPayment(YankiPaymentRequest request) {
        return RxJava3Adapter.monoToSingle(
                Mono.fromCallable(() -> validationSupport.validateOrThrow(request))
                        .then(p2pPaymentRules.validatePaymentRequest(request))
                        .then(walletRepository.findByPhoneNumber(request.getSenderPhoneNumber())
                                .switchIfEmpty(Mono.error(new RuntimeException("Sender wallet not found")))
                                .zipWith(walletRepository.findByPhoneNumber(request.getReceiverPhoneNumber())
                                        .switchIfEmpty(Mono.error(new RuntimeException("Receiver wallet not found"))))
                                .flatMap(tuple -> {
                                    YankiWalletDocument sender = tuple.getT1();
                                    YankiWalletDocument receiver = tuple.getT2();
                                    return p2pPaymentRules.requireBothWalletsActive(sender, receiver)
                                            .then(handleP2pIdempotencyOrExecute(sender, receiver, request));
                                })
                        )
        );
    }

    private Mono<YankiPaymentResponse> handleP2pIdempotencyOrExecute(YankiWalletDocument sender,
                                                                     YankiWalletDocument receiver,
                                                                     YankiPaymentRequest request) {
        String idempotencyKey = request.getIdempotencyKey();
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            return movementRepository.findByWalletIdAndIdempotencyKey(sender.getId(), idempotencyKey)
                    .flatMap(existing -> Mono.just(buildIdempotentP2pResponse(sender, receiver, existing)))
                    .switchIfEmpty(Mono.defer(() -> executeP2p(sender, receiver, request)));
        }
        return executeP2p(sender, receiver, request);
    }

    private YankiPaymentResponse buildIdempotentP2pResponse(YankiWalletDocument sender,
                                                            YankiWalletDocument receiver,
                                                            YankiMovementDocument existing) {
        YankiPaymentResponse resp = new YankiPaymentResponse();
        resp.setPaymentId(existing.getId());
        resp.setSenderWalletId(sender.getId());
        resp.setReceiverWalletId(receiver.getId());
        resp.setAmount(existing.getAmount());
        resp.setDescription(existing.getDescription());
        resp.setCreatedAt(OffsetDateTime.ofInstant(existing.getCreatedAt(), ZoneOffset.UTC));
        return resp;
    }

    private Mono<YankiPaymentResponse> executeP2p(YankiWalletDocument sender,
                                                  YankiWalletDocument receiver,
                                                  YankiPaymentRequest request) {
        return p2pPaymentRules.requireSenderBalance(sender, request.getAmount())
                .then(Mono.defer(() -> {
                    double senderBalance = sender.getBalance() != null ? sender.getBalance() : 0.0;
                    sender.setBalance(senderBalance - request.getAmount());
                    receiver.setBalance((receiver.getBalance() != null ? receiver.getBalance() : 0.0)
                            + request.getAmount());

                    Instant now = Instant.now();
                    String paymentId = UUID.randomUUID().toString();

                    YankiMovementDocument outMovement = movementFactory.p2pOut(
                            paymentId,
                            sender.getId(),
                            request.getAmount(),
                            request.getReceiverPhoneNumber(),
                            request.getDescription(),
                            request.getIdempotencyKey(),
                            now);

                    YankiMovementDocument inMovement = movementFactory.p2pIn(
                            receiver.getId(),
                            request.getAmount(),
                            request.getSenderPhoneNumber(),
                            request.getDescription(),
                            now);

                    return tx.transactional(
                            walletRepository.save(sender)
                                    .then(walletRepository.save(receiver))
                                    .then(movementRepository.save(outMovement))
                                    .then(movementRepository.save(inMovement))
                                    .thenReturn(new YankiPaymentResponse()
                                            .paymentId(paymentId)
                                            .senderWalletId(sender.getId())
                                            .receiverWalletId(receiver.getId())
                                            .amount(request.getAmount())
                                            .description(request.getDescription())
                                            .createdAt(OffsetDateTime.ofInstant(now, ZoneOffset.UTC)))
                                    .flatMap(response -> outboxEventRepository.save(
                                            yankiEventFactory.paymentCreated(
                                                    paymentId,
                                                    sender.getId(),
                                                    receiver.getId(),
                                                    request.getSenderPhoneNumber(),
                                                    request.getReceiverPhoneNumber(),
                                                    request.getAmount(),
                                                    request.getDescription(),
                                                    request.getIdempotencyKey()
                                            )).thenReturn(response))
                    );
                }))
                .flatMap(resp ->
                        evictYankiWalletByPhoneCache(sender.getPhoneNumber())
                                .then(evictYankiWalletByPhoneCache(receiver.getPhoneNumber()))
                                .thenReturn(resp)
                );
    }
}
