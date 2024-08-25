package ru.exdata.moex.utils.interfaces;

public interface SupplierMethodInvocation<T, E extends Exception> {

    T invoke() throws E;

}
