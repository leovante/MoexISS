package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.Securities;
import ru.exdata.moex.db.repository.SecuritiesRepository;
import ru.exdata.moex.dto.RequestParamSecurities;
import ru.exdata.moex.dto.SecuritiesDto;
import ru.exdata.moex.mapper.SecuritiesMapper;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Singleton
public class SecuritiesDao {

    private final SecuritiesRepository securitiesRepository;

    @Transactional
    public Flux<Securities> saveAll(List<SecuritiesDto> dtos) {
        return securitiesRepository.saveAll(SecuritiesMapper.fromDtoListToEntityList(dtos));
    }

    @Transactional
    public Flux<Securities> saveAllArray(List<Object[]> dtos) {
        return securitiesRepository.saveAll(SecuritiesMapper.fromArrToEntity(dtos));
    }

    @Transactional
    public Flux<Securities> save(SecuritiesDto dtos) {
        return securitiesRepository.saveAll(SecuritiesMapper.fromDtoToEntityList(dtos));
    }

    @Transactional
    public Mono<Securities> save(Object[] dtos) {
        if (dtos == null || dtos.length == 0) {
            return Mono.just(new Securities());
        }
        var sec = SecuritiesMapper.fromArrToEntity(dtos);
        return securitiesRepository
                .findById(sec.getId())
                .doOnNext(it -> {
                    securitiesRepository.update(sec).subscribe();
                })
                .switchIfEmpty(securitiesRepository.save(sec));
    }

    @Transactional
    public Flux<Securities> findByRequest(RequestParamSecurities request) {
        return securitiesRepository.findAllLikeSecId(request);
    }

}
