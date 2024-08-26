package ru.exdata.moex.utils;

import org.apache.commons.lang3.tuple.Pair;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class BackgroundExecutionRoutines {

    public static <R, E extends Exception> R calculateWithWait(ExecutorService executorService,
                                                               SupplierMethodInvocation<R, E> methodInvocation,
                                                               Integer timeout
//                                                             Supplier<R> interruptValueSupplier,
//                                                             MethodInvocationCallable.TimeoutConsumer<R, E> backgroundCallback
    )
            throws ExecutionException, InterruptedException, E {
        ReentrantLock lock = new ReentrantLock();
        AtomicBoolean flag = new AtomicBoolean(false);

        CompletableFuture<Pair<R, E>> future = CompletableFuture.supplyAsync(
                new MethodInvocationResultSupplier<>(methodInvocation, lock, flag/*, backgroundCallback*/), executorService);

        try {
            Pair<R, E> result = timeout != null ? future.get(timeout, java.util.concurrent.TimeUnit.MILLISECONDS) : future.get();
            E resultException = result.getRight();
            if (resultException != null) {
                throw resultException;
            }
            return result.getLeft();
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
//      return processExecutionTimeout(e, lock, flag, future, interruptValueSupplier);
        }
    }

    private static <R, E extends Exception> R processExecutionTimeout(TimeoutException e, ReentrantLock lock,
                                                                      AtomicBoolean flag,
                                                                      CompletableFuture<Pair<R, E>> future, Supplier<R> interruptValueSupplier)
            throws ExecutionException, InterruptedException, E {
        try {
            lock.lock();
            if (flag.get()) {
                Pair<R, E> result = future.get();
                E resultException = result.getRight();
                if (resultException != null) {
                    throw resultException;
                }
                return result.getLeft();
            } else {
                flag.set(true);
                return interruptValueSupplier.get();
            }
        } finally {
            lock.unlock();
        }
    }

    public static abstract class MethodInvocationCallable<R, E extends Exception> implements Callable<R>,
            SupplierMethodInvocation<R, E> {

        private boolean timeout;
        private TimeoutConsumer timeoutCallback;

        public MethodInvocationCallable(TimeoutConsumer timeoutCallback) {
            this.timeoutCallback = timeoutCallback;
        }

        @Override
        public R call() throws Exception {
            R result = null;
            try {
                result = invoke();
            } catch (Exception e) {
                if (timeout && timeoutCallback != null) {
                    timeoutCallback.error(e);
                }
                throw e;
            }

            if (timeout && timeoutCallback != null) {
                timeoutCallback.accept(result);
            }
            return result;
        }

        public void markTimeout() {
            timeout = true;
        }

        public interface TimeoutConsumer<R, E extends Exception> {

            void accept(R value);

            void error(E exception);
        }
    }

    private static class MethodInvocationResultSupplier<R, E extends Exception> implements Supplier<Pair<R, E>> {

        private final SupplierMethodInvocation<R, E> methodInvocation;
        private final ReentrantLock lock;
        private final AtomicBoolean flag;
//    private final MethodInvocationCallable.TimeoutConsumer<R, E> backgroundCallback;

        public MethodInvocationResultSupplier(SupplierMethodInvocation<R, E> methodInvocation, ReentrantLock lock,
                                              AtomicBoolean flag
//                                          MethodInvocationCallable.TimeoutConsumer<R, E> backgroundCallback
        ) {
            this.methodInvocation = methodInvocation;
            this.lock = lock;
            this.flag = flag;
//      this.backgroundCallback = backgroundCallback;
        }

        @Override
        public Pair<R, E> get() {
            R invocationResultValue = null;
            E invocationResultException = null;

            try {
                invocationResultValue = methodInvocation.invoke();
            } catch (Exception e) {
                invocationResultException = (E) e;
            }

      /*try {
        lock.lock();
        if (flag.get()) {
          if (backgroundCallback != null) {
            if (invocationResultException != null) {
              backgroundCallback.error(invocationResultException);
            } else {
              backgroundCallback.accept(invocationResultValue);
            }
          }
        } else {
          flag.set(true);
        }*/
            return Pair.of(invocationResultValue, invocationResultException);
      /*} finally {
        lock.unlock();
      }*/
        }
    }
}