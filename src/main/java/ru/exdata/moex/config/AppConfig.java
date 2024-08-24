package ru.exdata.moex.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import jakarta.inject.Singleton;
import ru.exdata.moex.db.repository.ResourceLockRepository;
import ru.exdata.moex.handler.event.lock.CrossTransactionalDBLockService;
import ru.exdata.moex.utils.lock.LockService;

@Factory
public class AppConfig {

    @Singleton
    @Primary
    public LockService crossTransactionalDBLockServiceBean(ResourceLockRepository resourceLockRepository,
                                                           R2dbcOperations r2dbcOperations
                                                           /*PlatformTransactionManager transactionManager,
                                                           EntityManagerFactory entityManagerFactory*/) {
        return new CrossTransactionalDBLockService(resourceLockRepository, r2dbcOperations);
    }

}
