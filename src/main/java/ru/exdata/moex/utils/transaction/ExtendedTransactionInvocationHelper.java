package ru.exdata.moex.utils.transaction;

public interface ExtendedTransactionInvocationHelper extends TransactionInvocationHelper {

    /*PlatformTransactionManager getTransactionManager();

    default <R, E extends Exception> R invokeWithTransaction(TransactionDefinition transactionDefinition,
                                                             SupplierMethodInvocation<R, E> methodInvocation) throws E {
        return invokeWithTransaction(getTransactionManager(), transactionDefinition, methodInvocation);
    }

    default <R, E extends Exception> R invokeWithTransaction(TransactionDefinitionProvider transactionDefinition,
                                                             SupplierMethodInvocation<R, E> methodInvocation) throws E {
        return invokeWithTransaction(getTransactionManager(), transactionDefinition, methodInvocation);
    }

    default TransactionDefinition newTransaction() {
        DefaultTransactionDefinition result = new DefaultTransactionDefinition();
        result.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return result;
    }

    default TransactionDefinition currentOrNewTransaction() {
        DefaultTransactionDefinition result = new DefaultTransactionDefinition();
        result.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return result;
    }

    default TransactionDefinition currentTransaction() {
        DefaultTransactionDefinition result = new DefaultTransactionDefinition();
        result.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return result;
    }

    default TransactionDefinitionProvider transaction(TransactionStatus transaction) {
        return new TransactionDefinitionProvider() {

            @Override
            public TransactionDefinition getTransactionDefinition(PlatformTransactionManager transactionManager) {
                throw new NotImplementedException();
            }

            @Override
            public TransactionStatus getTransaction(PlatformTransactionManager transactionManager) {
                return transaction;
            }
        };
    }*/

}
