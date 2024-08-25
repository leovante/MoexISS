package ru.exdata.moex.db.dao;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.db.repository.MoexSyncRepository;
import ru.exdata.moex.handler.event.SyncTypes;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class MoexSyncDao {

    private final MoexSyncRepository moexSyncRepository;

    @Transactional
    public @NonNull Mono<MoexSync> findByDataTypeOrderByUpdateDateDesc(SyncTypes types) {
        return moexSyncRepository.findByDataTypeOrderByUpdateDateDesc(types.name());
    }

    @Transactional
    public @NonNull Mono<MoexSync> save(MoexSync entity) {
        log.info("save MoexSync to db. type: {}", entity.getDataType());

        return Mono.just(entity)
                .flatMap(sec -> moexSyncRepository.findByDataTypeOrderByUpdateDateDesc(entity.getDataType())
                        .flatMap(moexSyncRepository::update)
                        .switchIfEmpty(moexSyncRepository.save(entity)))
//                .doOnCancel(() -> new Exception().printStackTrace())
                ;
    }

}
