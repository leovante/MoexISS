package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesCandlesAbstract;
import ru.exdata.moex.db.entity.SecuritiesCandlesPk;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesCandlesRepository<T extends SecuritiesCandlesAbstract, E extends SecuritiesCandlesPk>
        extends ReactiveStreamsCrudRepository<T, E> {

    @Override
    @NonNull
    default <S extends T> Mono<S> save(@NonNull S entity) {
        return null;
    }

    @Override
    @NonNull
    default <S extends T> Publisher<S> saveAll(@NonNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NonNull
    default <S extends T> Publisher<S> update(@NonNull S entity) {
        return null;
    }

    @Override
    @NonNull
    default <S extends T> Publisher<S> updateAll(@NonNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<T> findById(@NonNull E e) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Boolean> existsById(@NonNull E e) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<T> findAll() {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Long> count() {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Long> deleteById(@NonNull E e) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Long> delete(@NonNull T entity) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Long> deleteAll(@NonNull Iterable<? extends T> entities) {
        return null;
    }

    @Override
    @NonNull
    default Publisher<Long> deleteAll() {
        return null;
    }

}
