package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesCandlesM1;
import ru.exdata.moex.db.entity.SecuritiesCandlesPk;

import java.time.LocalDate;
import java.time.LocalDateTime;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesCandlesM1Repository/*<T extends SecuritiesCandlesAbstract, E extends SecuritiesCandlesPk> */
        extends SecuritiesCandlesRepository<SecuritiesCandlesM1, SecuritiesCandlesPk> {

    @Query(value = ""
            + "select * "
            + "from securities_candles_m1 t "
            + "where t.begin_at >= cast(:date as timestamp) "
            + "  and t.begin_at < cast(:date as timestamp) + interval '1 day' "
            + "  and t.sec_id = UPPER(:security) "
            + "order by t.begin_at asc "
            + "")
    Flux<SecuritiesCandlesM1> findAllByBeginAtAndSecurity(LocalDate date, String security);

    @Query(value = ""
            + "select * "
            + "from securities_candles_m1 t "
            + "where t.begin_at = cast(:beginAt as timestamp) "
            + "  and t.end_at = cast(:endAt as timestamp)"
            + "  and t.sec_id = UPPER(:secId) "
            + "order by t.begin_at asc "
            + "")
    Mono<SecuritiesCandlesM1> findByPk(String secId, LocalDateTime beginAt, LocalDateTime endAt);

    @Override
    @NonNull <S extends SecuritiesCandlesM1> Mono<S> save(@NonNull S entity);

}
