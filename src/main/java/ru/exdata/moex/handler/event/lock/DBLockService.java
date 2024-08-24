package ru.exdata.moex.handler.event.lock;

import io.micronaut.transaction.TransactionDefinition;
import io.micronaut.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.exdata.moex.db.entity.ResourceLockEntity;
import ru.exdata.moex.db.repository.ResourceLockRepository;
import ru.exdata.moex.utils.Assertions;
import ru.exdata.moex.utils.DefaultAssertions;
import ru.exdata.moex.utils.Routines;
import ru.exdata.moex.utils.lock.LockService;
import ru.exdata.moex.utils.lock.ResourceLock;
import ru.exdata.moex.utils.lock.SynchronizedResource;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class DBLockService implements LockService {

    private final ResourceLockRepository resourceLockRepository;
    private final TransactionalService transactionalService;
//    private final PlatformTransactionManager transactionManager;

    @Override
    public ResourceLock acquireLock(SynchronizedResource resource) {
        return acquireLock(resource, null);
    }

    @Override
    public ResourceLock acquireLock(SynchronizedResource resource, LocalDateTime expirationDate)
            throws LockBusyException {
        return transactionalService.executeInNewTransaction(() -> {
            String resourceName = DefaultAssertions.isNotNull(DefaultAssertions.isNotNull(resource).getLockKey());
            try {
                ResourceLockEntity lock = resourceLockRepository.save(ResourceLockEntity.builder()
                                .resourceName(resourceName)
                                .dateCreate(LocalDateTime.now())
                                .dateExpire(expirationDate)
                                .build())
                        .log("DBLockService | acquireLock")
                        .toFuture().get();
                return new DBResourceLock(lock);
            } catch (RuntimeException d) {
                throw new LockBusyException("Ресурс занят: " + resource);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка получения блокировки ресурса: ", e);
            }
        });
    }

    @Override
    public boolean isResourceNotLocked(SynchronizedResource resource) {
        String resourceName = DefaultAssertions.isNotNull(DefaultAssertions.isNotNull(resource).getLockKey());
        return Routines.isEmpty(resourceLockRepository.findByResourceName(resourceName).subscribe());
    }

    @Override
    @Transactional(propagation = TransactionDefinition.Propagation.REQUIRES_NEW)
    public void releaseLock(ResourceLock lock) {
        DBResourceLock resourceLock = Assertions.isInstance(lock, DBResourceLock.class,
                () -> new LockException("Тип блокировки не поддерживается"));
        ResourceLockEntity lockData = resourceLock.getLock();
        resourceLockRepository.delete(lockData)
                .log()
                .subscribe();
    }

    public static class DBResourceLock implements ResourceLock {

        private ResourceLockEntity lock;

        public DBResourceLock() {
        }

        public DBResourceLock(ResourceLockEntity lock) {
            this.lock = lock;
        }

        public ResourceLockEntity getLock() {
            return lock;
        }
    }

}
