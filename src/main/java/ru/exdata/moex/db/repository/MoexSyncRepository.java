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
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.db.entity.Securities;
import ru.exdata.moex.db.entity.SecuritiesTrades;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface MoexSyncRepository extends ReactiveStreamsCrudRepository<MoexSync, Long> {

    @NonNull Mono<MoexSync> findByDataTypeOrderByUpdateDateDesc(@NonNull String dataType);

    @Override
    @SingleResult
    @NonNull <S extends MoexSync> Mono<S> save(@NonNull S entity);

    @Override
    @SingleResult
    @NonNull <S extends MoexSync> Mono<S> update(@NonNull S entity);
}
