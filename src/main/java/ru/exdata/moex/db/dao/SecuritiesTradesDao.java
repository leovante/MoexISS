package ru.exdata.moex.db.dao;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesTrades;
import ru.exdata.moex.db.repository.SecuritiesTradesRepository;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;
import ru.exdata.moex.mapper.SecuritiesTradesMapper;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesTradesDao {

    private final SecuritiesTradesRepository securitiesTradesRepository;

    @Transactional
    public @NonNull Mono<SecuritiesTrades> findLast(RequestParamSecuritiesTrades request) {
        log.info("fetch trades from db. date:now() secId:{}", request.getSecurity());
        return securitiesTradesRepository.findOneBySysTimeAndSecurityOrderByTradeNoDesc(
                        LocalDate.now().toString(),
                        request.getSecurity());
    }

    @Transactional
    public Mono<SecuritiesTrades> save(Object[] dtos) {
        if (dtos == null || dtos.length == 0) {
            return Mono.just(new SecuritiesTrades());
        }
        var mapped = SecuritiesTradesMapper.fromArrToEntity(dtos);
        return securitiesTradesRepository
                .findByTradeNo(mapped.getTradeNo())
                .switchIfEmpty(securitiesTradesRepository.save(mapped));
    }

}
