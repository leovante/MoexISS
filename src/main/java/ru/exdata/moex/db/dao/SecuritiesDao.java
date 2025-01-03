package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.Securities;
import ru.exdata.moex.db.repository.SecuritiesRepository;
import ru.exdata.moex.dto.securities.Row;
import ru.exdata.moex.mapper.SecuritiesMapper;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesDao {

    private final SecuritiesRepository securitiesRepository;

    @Transactional
    public Mono<Securities> save(Row dto) {
        return Mono.fromCallable(() -> SecuritiesMapper.fromDtoToEntity(dto))
                .flatMap(sec -> securitiesRepository.findById(sec.getId())
                        .flatMap(existingSec -> {
                            return securitiesRepository.update(sec)
                                    .doOnSuccess(updated -> log.debug("Security updated: {}", updated));
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                            return securitiesRepository.save(sec)
                                    .doOnSuccess(saved -> log.debug("Security saved: {}", saved));
                        })))
                .doOnCancel(() -> log.warn("Pipeline Security canceled"))
                .onErrorResume(e -> {
                    log.error("Error occurred: " + e.getMessage());
                    return Mono.empty();
                });
    }

}
