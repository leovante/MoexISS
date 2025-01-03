package ru.exdata.moex.db.dao;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesSpec;
import ru.exdata.moex.db.records.rSecuritiesSpec;
import ru.exdata.moex.db.repository.SecuritiesSpecRepository;
import ru.exdata.moex.dto.securitiesSpec.Row;
import ru.exdata.moex.mapper.SecuritiesSpecMapper;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesSpecDao {

    private final SecuritiesSpecRepository securitiesSpecRepository;

    @Transactional
    public Flux<SecuritiesSpec> findBySecId(String secId) {
        return securitiesSpecRepository.findBySecId(secId);
    }

    @Transactional
    public Flux<SecuritiesSpec> save(Row dto, String secId) {
        return Mono.fromCallable(() -> SecuritiesSpecMapper.fromDtoToEntity(dto, secId))
                .flatMapMany(sec -> securitiesSpecRepository.findBySecIdAndName(secId, dto.getName())
                        .filter(element -> !element.getValuee().equalsIgnoreCase(sec.getValuee()))
                        .flatMap(element -> securitiesSpecRepository.delete(element)
                                .then(securitiesSpecRepository.save(sec))
                                .doOnSuccess(saved -> log.debug("SecuritiesSpec updated: {}", saved)))
                        .switchIfEmpty(securitiesSpecRepository.save(sec)
                                .doOnSuccess(saved -> log.debug("SecuritiesSpec saved: {}", saved))))
                .doOnCancel(() -> log.warn("Pipeline SecuritiesSpec canceled"))
                .onErrorResume(e -> {
                    log.error("Error occurred: " + e.getMessage());
                    return Flux.empty();
                });
    }

    @Transactional
    public @NonNull Flux<rSecuritiesSpec> findByListLvl(Integer lvl) {
        return securitiesSpecRepository.findByListing(lvl.toString());
    }

}
