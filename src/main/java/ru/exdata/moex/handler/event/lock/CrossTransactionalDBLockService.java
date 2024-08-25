package ru.exdata.moex.handler.event.lock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.ResourceLockEntity;
import ru.exdata.moex.db.repository.ResourceLockRepository;
import ru.exdata.moex.handler.event.lock.DBLockService.DBResourceLock;
import ru.exdata.moex.utils.Assertions;
import ru.exdata.moex.utils.DefaultAssertions;
import ru.exdata.moex.utils.Routines;
import ru.exdata.moex.utils.lock.LockService;
import ru.exdata.moex.utils.lock.ResourceLock;
import ru.exdata.moex.utils.lock.SynchronizedResource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Slf4j
public class CrossTransactionalDBLockService implements LockService {

    private final ResourceLockRepository resourceLockRepository;
    private final R2dbcOperations r2dbcOperations;

    @Override
    public ResourceLock acquireLock(SynchronizedResource resource) throws ExecutionException, InterruptedException {
        return acquireLock(resource, null);
    }

    @Override
    public ResourceLock acquireLock(SynchronizedResource resource, LocalDateTime expirationDate)
            throws LockBusyException, ExecutionException, InterruptedException {
        String resourceName = DefaultAssertions.isNotNull(DefaultAssertions.isNotNull(resource).getLockKey());
        var now = LocalDateTime.now();
        var expire = now.plusHours(1L);

        ResourceLockEntity resourceLockEntity = ResourceLockEntity.builder()
                .id(UUID.randomUUID())
                .resourceName(resourceName)
                .dateCreate(LocalDateTime.now())
                .dateExpire(expire)
                .build();

        String sql = String.format("""
                        insert into resource_lock
                        values ('%s', '%s', '%s', '%s')
                        """,
                resourceLockEntity.getId(),
                resourceLockEntity.getResourceName(),
                toTimestamp(resourceLockEntity.getDateCreate()),
                toTimestamp(resourceLockEntity.getDateExpire()));

        var cf = r2dbcOperations.connectionFactory().create();
        var lock = Mono.from(cf)
                .flatMap(c -> Mono.from(c.setTransactionIsolationLevel(IsolationLevel.READ_UNCOMMITTED))
                        .then(Mono.from(c.setAutoCommit(false)))
                        .then(Mono.from(c.beginTransaction()))
                        .then(Mono.from(c.createStatement(sql).execute())
                                .flatMap(result -> Mono.from(result.getRowsUpdated())
                                        .doOnNext(i -> log.info("lock success, locked rows count: {}, lock name: {}", i, resourceName)))
                                .onErrorResume(ex -> Mono.from(c.rollbackTransaction())
                                        .then(Mono.from(c.close()))
                                        .then(Mono.error(() -> {
                                            if (Routines.findException(ex, item -> Routines.isInstanceOf(item,
                                                    R2dbcDataIntegrityViolationException.class)) != null) {
                                                return new LockBusyException("Ресурс занят: " + resource);
                                            }
                                            return new RuntimeException("Ошибка получения блокировки ресурса: ", ex);
                                        })))
                                .log("SecuritiesCandlesSync | acquireLock")
                        ))
                .toFuture().get();


        return new CrossTransactionalResourceLock(resourceLockEntity, cf);
    }

    @Override
    public boolean isResourceNotLocked(SynchronizedResource resource) {
        String resourceName = DefaultAssertions.isNotNull(DefaultAssertions.isNotNull(resource).getLockKey());
        return Routines.isEmpty(resourceLockRepository.findByResourceName(resourceName).subscribe());
    }

    @Override
    public void releaseLock(ResourceLock lock) {
        CrossTransactionalResourceLock resourceLock = Assertions.isInstance(lock, CrossTransactionalResourceLock.class,
                () -> new LockException("Тип блокировки не поддерживается"));

        var resourceName = resourceLock.getLock().getResourceName();
        String sql = String.format("""
                        delete from resource_lock
                        where resource_name='%s'
                        """,
                resourceName);

        var cf = resourceLock.getConnection();
        Mono.from(cf)
                .flatMap(c -> Mono.from(c.createStatement(sql).execute())
                        .flatMap(result -> Mono.from(result.getRowsUpdated())
                                .doOnNext(i -> log.info("lock realise success, unlocked rows count: {}, lock name: {}", i, resourceName)))
                        .then(Mono.from(c.commitTransaction()))
                        .doFinally((st) -> Mono.just(c.close()).subscribe())
                        .log("SecuritiesCandlesSync | releaseLock")
                )
                .subscribe();
    }

    private Timestamp toTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static class CrossTransactionalResourceLock extends DBResourceLock {

        @Transient
        @JsonIgnore
        private final Publisher<? extends Connection> connection;

        public CrossTransactionalResourceLock() {
//            entityManager = null;
//            transaction = null;
            connection = null;
        }

        public CrossTransactionalResourceLock(ResourceLockEntity lock,
                                              Publisher<? extends Connection> connection) {
            super(lock);
            this.connection = connection;
        }

        public Publisher<? extends Connection> getConnection() {
            return connection;
        }

    }

}
