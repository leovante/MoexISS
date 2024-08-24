package ru.exdata.moex.handler.event.lock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.data.annotation.Transient;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.IsolationLevel;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

@RequiredArgsConstructor
@Slf4j
public class CrossTransactionalDBLockService implements LockService {

    private final ResourceLockRepository resourceLockRepository;
    private final R2dbcOperations r2dbcOperations;

    @Override
    public ResourceLock acquireLock(SynchronizedResource resource) {
        return acquireLock(resource, null);
    }

    @SneakyThrows
    @Override
    public ResourceLock acquireLock(SynchronizedResource resource, LocalDateTime expirationDate)
            throws LockBusyException {
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
                                        .doOnNext(i -> log.info("lock success, locked rows count: " + i)))
                                .onErrorResume(ex -> Mono.from(c.rollbackTransaction())
                                        .then(Mono.from(c.close()))
                                        .then(Mono.error(() -> {
                                            if (Routines.findException(ex, item -> Routines.isInstanceOf(item, ConstraintViolationException.class)) != null) {
                                                return new LockBusyException("Ресурс занят: " + resource);
                                            }
                                            return new RuntimeException("Ошибка получения блокировки ресурса: ", ex);
                                        })))
                                .log("SecuritiesCandlesSync | acquireLock")
                        ))
                .toFuture().get();

            /*r2dbcOperations.withTransaction(status ->
                    Mono.just(status.getConnection())
                            .flatMap(c -> Mono.from(c.setTransactionIsolationLevel(IsolationLevel.READ_UNCOMMITTED))
                                            .then(Mono.from(c.setAutoCommit(false)))
                                            .then(Mono.from(c.beginTransaction()))
                                            .then(Mono.from(c.createStatement("sql").execute()))
                                            .flatMap(result -> Mono.from(result.getRowsUpdated()))
//                                            .then(Mono.from(c.commitTransaction()))
//                                            .onErrorResume(ex -> Mono.from(c.rollbackTransaction()).then(Mono.error(ex)))
//                                            .doFinally((st)->c.close()));
                            ));*/
//            var sqlPreparedQuery = new DefaultSqlPreparedQuery(sqlPreparedQuery1);
//            sqlPreparedQuery.prepare(ResourceLockEntity.builder().build());
//            reactorReactiveRepositoryOperations.persist(sqlPreparedQuery)
            /*entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            ResourceLockEntity resourceLockEntity = ResourceLockEntity.builder()
                    .resourceName(resourceName)
                    .createDate(LocalDateTime.now())
                    .expireDate(expirationDate)
                    .build();
            entityManager.persist(resourceLockEntity);
            entityManager.flush();
           return new CrossTransactionalResourceLock(resourceLockEntity, entityManager, transaction);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            if (entityManager != null) entityManager.close();
            if (Routines.findException(e, item -> Routines.isInstanceOf(item, ConstraintViolationException.class)) != null) {
                throw new LockBusyException("Ресурс занят: " + resource);
            }
            throw new RuntimeException("Ошибка получения блокировки ресурса: ", e);*/
        return new CrossTransactionalResourceLock(resourceLockEntity, cf);
    }

    @Override
    public boolean isResourceNotLocked(SynchronizedResource resource) {
        String resourceName = DefaultAssertions.isNotNull(DefaultAssertions.isNotNull(resource).getLockKey());
        return Routines.isEmpty(resourceLockRepository.findByResourceName(resourceName).subscribe());
    }

    @SneakyThrows
    @Override
    public void releaseLock(ResourceLock lock) {
        CrossTransactionalResourceLock resourceLock = Assertions.isInstance(lock, CrossTransactionalResourceLock.class,
                () -> new LockException("Тип блокировки не поддерживается"));

        String sql = String.format("""
                        delete from resource_lock
                        where resource_name='%s'
                        """,
                resourceLock.getLock().getResourceName());

        var cf = resourceLock.getConnection();
        Mono.from(cf)
                .flatMap(c -> Mono.from(c.createStatement(sql).execute())
                        .then(Mono.from(c.commitTransaction()))
                        .doFinally((st) -> Mono.just(c.close()).subscribe())
                )
                .toFuture().get();
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
