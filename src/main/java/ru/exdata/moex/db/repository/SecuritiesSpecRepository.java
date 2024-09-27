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
            with securities as (select distinct on (isin) *
                                from securities
                                where is_traded = 1),
                 spec as (select sp.valuee as isin, sp.sec_id
                          from securities se
                                   join securities_spec sp on se.sec_id = sp.sec_id
                          where sp.name = 'ISIN'),
                 spec2 as (select sp.*
                           from spec sp
                                    join securities_spec sp2 on sp.sec_id = sp2.sec_id
                           where sp2.name = 'LISTLEVEL'
                             and sp2.valuee = :lvl)
            select sec_id, isin
            from spec2
            order by sec_id;
            """)
    @NonNull Flux<rSecuritiesSpec> findByListing(String lvl);

}
