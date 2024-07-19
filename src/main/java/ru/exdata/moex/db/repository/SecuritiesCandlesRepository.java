package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesCandles;
import ru.exdata.moex.db.entity.SecuritiesCandlesPk;

import java.time.LocalDate;
import java.time.LocalDateTime;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesCandlesRepository extends ReactiveStreamsCrudRepository<SecuritiesCandles, SecuritiesCandlesPk> {

    @Query(value = ""
            + "select * "
            + "from securities_candles t "
            + "where t.begin_at >= cast(:date as timestamp) "
            + "  and t.begin_at < cast(:date as timestamp) + interval '1 day' "
            + "  and t.sec_id = UPPER(:security) "
            + "order by t.begin_at asc "
            + "")
    @NonNull Flux<SecuritiesCandles> findAllByBeginAtAndSecurity(@NonNull LocalDate date, @NonNull String security);

    @Query(value = ""
            + "select * "
            + "from securities_candles t "
            + "where t.begin_at = cast(:beginAt as timestamp) "
            + "  and t.end_at = cast(:endAt as timestamp)"
            + "  and t.sec_id = UPPER(:secId) "
            + "order by t.begin_at asc "
            + "")
    @NonNull Mono<SecuritiesCandles> findByPk(String secId, LocalDateTime beginAt, LocalDateTime endAt);

    @Override
    @SingleResult
    @NonNull <S extends SecuritiesCandles> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull <S extends SecuritiesCandles> Flux<S> saveAll(@NonNull Iterable<S> entities);

}
