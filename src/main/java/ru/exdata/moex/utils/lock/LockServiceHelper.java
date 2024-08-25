package ru.exdata.moex.utils.lock;


import ru.exdata.moex.utils.Routines;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;
import ru.exdata.moex.utils.lock.LockService.LockBusyException;

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
            } catch (LockBusyException | ExecutionException | InterruptedException e) {
                if (Routines.findException(e, item -> Routines.isInstanceOf(item, LockBusyException.class)) != null)
                    if (System.currentTimeMillis() - start < timeout) {
                        Routines.sleep(2000L);
                    } else {
                        throw new RuntimeException(e);
                    }
            }
        }

        try {
            return methodInvocation.invoke();
        } finally {
            lockService.releaseLock(lock);
        }
    }
}
