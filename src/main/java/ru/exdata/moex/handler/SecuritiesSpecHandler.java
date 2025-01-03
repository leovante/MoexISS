package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesSpecDao;
import ru.exdata.moex.dto.securitiesSpec.Data;
import ru.exdata.moex.dto.securitiesSpec.DataDescription;
import ru.exdata.moex.dto.securitiesSpec.Row;
import ru.exdata.moex.handler.client.SecuritiesSpecApiClient;
import ru.exdata.moex.mapper.SecuritiesSpecMapper;

import java.util.List;
import java.util.Objects;

/**
 * Получение спецификации акции списком.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesSpecHandler {

    private final SecuritiesSpecApiClient securitiesSpecApiClient;
    private final SecuritiesSpecDao securitiesSpecDao;

    public Flux<Row> fetchBySecId(String secId) {
        return securitiesSpecDao.findBySecId(secId)
                .flatMap(sec -> Mono.fromCallable(() -> sec)
                        .doOnNext(entity -> log.debug("Found entity: {}", entity))
                        .filter(Objects::nonNull)
                        .map(SecuritiesSpecMapper::fromEntityToDto)
                        .doOnNext(dto -> log.debug("Mapped to DTO: {}", dto)))
                .switchIfEmpty(Flux.defer(() -> fetchSecuritiesSpecs(secId))
                        .publishOn(Schedulers.boundedElastic())
                        .doOnNext(it -> securitiesSpecDao.save(it, secId).subscribe()))
                .doOnCancel(() -> log.warn("Pipeline canceled"))
                .onErrorResume(e -> {
                    log.error("Error occurred: {}", e.getMessage());
                    return Flux.empty(); // Propagate the error
                });
    }

    private Flux<Row> fetchSecuritiesSpecs(String secId) {
        return securitiesSpecApiClient.fetch(secId)
                .filter(it -> !it.getData().isEmpty())
                .map(it -> getDataById(it.getData(), DataDescription.class))
                .filter(it -> !it.getRows().isEmpty())
                .flatMapIterable(DataDescription::getRows)
                ;
    }

    private <T> T getDataById(List<Data> data, Class<T> clazz) {
        return (T) data.stream().filter(it -> clazz.isInstance(it)).findFirst()
                .orElseThrow();
    }

}
