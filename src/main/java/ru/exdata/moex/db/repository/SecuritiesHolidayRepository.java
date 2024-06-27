package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesHoliday;

import java.time.LocalDate;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesHolidayRepository extends ReactiveStreamsCrudRepository<SecuritiesHoliday, Long> {

    @Override
    @NonNull <S extends SecuritiesHoliday> Mono<S> save(@NonNull S entity);

    @NonNull Mono<SecuritiesHoliday> findByBoardIdAndTradeDateAndSecId(@NonNull String boardId,
                                                                       @NonNull LocalDate tradeDate,
                                                                       @NonNull String secId);

}
