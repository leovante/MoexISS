package ru.exdata.moex.utils.lock;


import ru.exdata.moex.utils.Routines;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;

import java.util.concurrent.ExecutionException;

/**
 * Хелпер для работы с блокировками ресурсов
 */
public interface LockServiceHelper {

    default <T, E extends Exception> T invokeWithLock(LockService lockService,
                                                      SynchronizedResource resource,
                                                      Long timeout,
                                                      SupplierMethodInvocation<T, E> methodInvocation) throws E {
        ResourceLock lock = null;

        long start = System.currentTimeMillis();
        while (true) {
            try {
                lock = lockService.acquireLock(resource);
                break;
            } catch (LockService.LockBusyException e) {
                if (System.currentTimeMillis() - start < timeout) {
                    Routines.sleep(2000L);
                } else {
                    throw e;
                }
            }
        }

        try {
            return methodInvocation.invoke().last().toFuture().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lockService.releaseLock(lock);
        }
    }
}
