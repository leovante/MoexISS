package ru.exdata.moex.utils.transaction;

public interface TransactionInvocationHelper {
/*
    default <R, E extends Exception> R invokeWithTransaction(PlatformTransactionManager transactionManager,
                                                             TransactionDefinition transactionDefinition, SupplierMethodInvocation<R, E> methodInvocation) throws E {
        return this.<R, E>invokeWithTransaction(transactionManager, transactionDefinition, transaction ->
                methodInvocation.invoke()
        );
    }

    default <R, E extends Exception> R invokeWithTransaction(PlatformTransactionManager transactionManager,
                                                             TransactionDefinitionProvider transactionDefinition, SupplierMethodInvocation<R, E> methodInvocation) throws E {
        return this.<R, E>invokeWithTransaction(transactionManager, transactionDefinition, transaction ->
                methodInvocation.invoke()
        );
    }

    default <R, E extends Exception> R invokeWithTransaction(PlatformTransactionManager transactionManager,
                                                             TransactionDefinition transactionDefinition, ThrowsFunction<TransactionContext, R, E> methodInvocation) throws E {
        return invokeWithTransaction(transactionManager, new TransactionDefinitionProvider() {
            @Override
            public TransactionDefinition getTransactionDefinition(PlatformTransactionManager transactionManager) {
                return transactionDefinition;
            }
        }, methodInvocation);
    }

    default <R, E extends Exception> R invokeWithTransaction(PlatformTransactionManager transactionManager,
                                                             TransactionDefinitionProvider transactionDefinition, ThrowsFunction<TransactionContext, R, E> methodInvocation) throws E {
        if (transactionManager != null && transactionDefinition != null) {
            TransactionStatus transaction = transactionDefinition.getTransaction(transactionManager);
            ObjectValueHolder<Boolean> commit = new ObjectValueHolder<>(false);
            ObjectValueHolder<Boolean> rollback = new ObjectValueHolder<>(false);
            try {
                R result = methodInvocation.apply(new TransactionContext() {
                    @Override
                    public void commit() {
                        transactionManager.commit(transaction);
                        commit.setValue(true);
                    }

                    @Override
                    public void rollback() {
                        transactionManager.rollback(transaction);
                        rollback.setValue(true);
                    }
                });
                if (!Routines.isTrue(commit.getValue())) {
                    transactionManager.commit(transaction);
                }
                return result;
            } catch (Exception e) {
                if (!Routines.isTrue(rollback.getValue())) {
                    transactionManager.rollback(transaction);
                }
                throw e;
            }
        } else {
            return methodInvocation.apply(new TransactionContext() {
            });
        }
    }

    interface TransactionContext {

        default void commit() {
            throw new NotImplementedException();
        }

        default void rollback() {
            throw new NotImplementedException();
        }
    }

    interface TransactionDefinitionProvider {
        TransactionDefinition getTransactionDefinition(PlatformTransactionManager transactionManager);

        default TransactionStatus getTransaction(PlatformTransactionManager transactionManager) {
            return transactionManager.getTransaction(getTransactionDefinition(transactionManager));
        }
    }*/
}
