package nttdata.bootcamp.yanki_service.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * Reactive MongoDB transaction manager and {@link TransactionalOperator} for wallet + outbox atomicity.
 */
@Configuration
public class MongoTxConfig {

    /**
     * @param factory reactive Mongo database factory
     * @return transaction manager
     */
    @Bean
    public ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory factory) {
        return new ReactiveMongoTransactionManager(factory);
    }

    /**
     * @param txManager transaction manager
     * @return operator used by services for transactional flows
     */
    @Bean
    public TransactionalOperator transactionalOperator(ReactiveMongoTransactionManager txManager) {
        return TransactionalOperator.create(txManager);
    }
}
