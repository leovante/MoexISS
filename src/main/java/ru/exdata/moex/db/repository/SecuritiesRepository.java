package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.dto.RequestParamSecurities;
import ru.exdata.moex.db.entity.Securities;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesRepository extends ReactiveStreamsCrudRepository<Securities, Integer> {

    @Override
    @NonNull Mono<Securities> findById(@NonNull Integer integer);

    @Override
    @SingleResult
    @NonNull <S extends Securities> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull <S extends Securities> Mono<S> update(@NonNull S entity);

    @Override
    @NonNull <S extends Securities> Flux<S> saveAll(@NonNull Iterable<S> entities);

    @Query(value = "")
    Flux<Securities> findAllLikeSecId(RequestParamSecurities secId);

}
