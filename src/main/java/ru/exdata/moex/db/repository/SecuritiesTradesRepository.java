package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesTrades;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesTradesRepository extends ReactiveStreamsCrudRepository<SecuritiesTrades, Long> {

    @NonNull Mono<SecuritiesTrades> findByTradeNo(@NonNull Long integer);
    @Query(value = ""
            + "select * "
            + "from securities_trades t "
            + "where t.sys_time >= cast(:date as timestamp) "
            + "  and t.sys_time < cast(:date as timestamp) + interval '1 day' "
            + "  and t.sec_id = UPPER(:security) "
            + "order by t.trade_no desc "
            + "")
    @NonNull Mono<SecuritiesTrades> findOneBySysTimeAndSecurityOrderByTradeNoDesc(@NonNull String date, @NonNull String security);

    @Override
    @SingleResult
    @NonNull <S extends SecuritiesTrades> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull <S extends SecuritiesTrades> Flux<S> saveAll(@NonNull Iterable<S> entities);

    @Query(value = "")
    Flux<SecuritiesTrades> findAllLikeSecId(RequestParamSecuritiesTrades secId);

}
