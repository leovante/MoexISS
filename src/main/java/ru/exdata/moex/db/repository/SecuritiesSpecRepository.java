package ru.exdata.moex.db.repository;


import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesSpec;
import ru.exdata.moex.db.records.rSecuritiesSpec;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SecuritiesSpecRepository extends ReactiveStreamsCrudRepository<SecuritiesSpec, Integer> {

    @NonNull Flux<SecuritiesSpec> findBySecId(@NonNull String secId);

    @NonNull Flux<SecuritiesSpec> findBySecIdAndName(@NonNull String secId, @NonNull String name);

    @Override
    @SingleResult
    @NonNull <S extends SecuritiesSpec> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull <S extends SecuritiesSpec> Mono<S> update(@NonNull S entity);

    @Override
    @NonNull Mono<Long> delete(@NonNull SecuritiesSpec entity);

    @Query(value = """
            with init as (select sec_id, name, valuee
                          from securities_spec
                          group by sec_id, name, valuee
                          having name = 'LISTLEVEL'
                              or name = 'ISIN'
                          order by sec_id)
            select sec_id, valuee as isin
            from init
            where name = 'ISIN';
            """)
    @NonNull Flux<rSecuritiesSpec> findByListLvl(String lvl);

}
