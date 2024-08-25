package ru.exdata.moex.utils.lock;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

/**
 * Сервис синхронизации по ресурсу
 */
public interface LockService {

    ResourceLock acquireLock(SynchronizedResource resource) throws LockBusyException, ExecutionException, InterruptedException;

    ResourceLock acquireLock(SynchronizedResource resource, LocalDateTime expirationDate) throws LockBusyException, ExecutionException, InterruptedException;

    boolean isResourceNotLocked(SynchronizedResource resource);

    void releaseLock(ResourceLock lock);

    class LockException extends RuntimeException {

        public LockException() {
        }

        public LockException(String message) {
            super(message);
        }

        public LockException(String message, Throwable cause) {
            super(message, cause);
        }

        public LockException(Throwable cause) {
            super(cause);
        }

        public LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    class LockBusyException extends LockException {

        public LockBusyException() {
        }

        public LockBusyException(String message) {
            super(message);
        }

        public LockBusyException(String message, Throwable cause) {
            super(message, cause);
        }

        public LockBusyException(Throwable cause) {
            super(cause);
        }

        public LockBusyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
