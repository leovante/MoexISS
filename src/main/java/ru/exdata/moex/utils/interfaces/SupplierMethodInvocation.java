package ru.exdata.moex.utils.interfaces;

import reactor.core.publisher.Flux;

public interface SupplierMethodInvocation<T, E extends Exception> {

    Flux<T> invoke() throws E;

}
