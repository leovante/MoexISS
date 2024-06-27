package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesHistory;

import java.time.LocalDate;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesHistoryRepository extends ReactiveStreamsCrudRepository<SecuritiesHistory, Long> {

    @Override
    @NonNull Mono<SecuritiesHistory> findById(@NonNull Long id);

    @NonNull Mono<SecuritiesHistory> findByTradeDateAndBoardIdAndSecIdAndValue(@NonNull LocalDate tradeDate,
                                                                               @NonNull String boardId,
                                                                               @NonNull String secId,
                                                                               @NonNull Double value);

    @NonNull Mono<SecuritiesHistory> findByTradeDateAndBoardIdAndSecId(@NonNull LocalDate tradeDate,
                                                                       @NonNull String boardId,
                                                                       @NonNull String secId);

    @NonNull Flux<SecuritiesHistory> findByTradeDate(@NonNull LocalDate date);

    @Override
    @SingleResult
    @NonNull <S extends SecuritiesHistory> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull <S extends SecuritiesHistory> Flux<S> saveAll(@NonNull Iterable<S> entities);

}
