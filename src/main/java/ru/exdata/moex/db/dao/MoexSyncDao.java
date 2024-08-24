package ru.exdata.moex.db.dao;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.db.repository.MoexSyncRepository;
import ru.exdata.moex.handler.event.SyncTypes;

@RequiredArgsConstructor
@Singleton
public class MoexSyncDao {

    private final MoexSyncRepository moexSyncRepository;

    @Transactional
    public @NonNull Mono<MoexSync> findByDataTypeOrderByUpdateDateDesc(SyncTypes types) {
        return moexSyncRepository.findByDataTypeOrderByUpdateDateDesc(types.value);
    }

    @Transactional
    public @NonNull Mono<MoexSync> save(MoexSync entity) {
        return Mono.just(entity)
                .flatMap(sec -> Mono.from(moexSyncRepository.findByDataTypeOrderByUpdateDateDesc(entity.getDataType()))
                        .flatMap(update -> Mono.from(moexSyncRepository.update(update)))
                        .switchIfEmpty(moexSyncRepository.save(sec)))
//                .doOnCancel(() -> new Exception().printStackTrace())
                ;
    }

}
