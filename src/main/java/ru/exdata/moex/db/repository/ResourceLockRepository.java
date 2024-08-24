package ru.exdata.moex.db.repository;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.ResourceLockEntity;

import java.util.UUID;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface ResourceLockRepository extends ReactiveStreamsCrudRepository<ResourceLockEntity, UUID> {

    @NonNull Flux<ResourceLockEntity> findByResourceName(@NonNull String resourceName);

    @Override
    @NonNull <S extends ResourceLockEntity> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull Mono<Long> delete(@NonNull ResourceLockEntity entity);

}
