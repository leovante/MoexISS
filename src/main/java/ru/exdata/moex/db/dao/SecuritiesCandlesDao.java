package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesCandles;
import ru.exdata.moex.db.repository.SecuritiesCandlesRepository;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.mapper.SecuritiesCandlesMapper;

@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesDao {

    private final SecuritiesCandlesRepository securitiesCandlesRepository;

    @Transactional
    public Flux<SecuritiesCandles> findAllByBeginAtAndSecurity(RequestParamSecuritiesCandles request) {
        return securitiesCandlesRepository.findAllByBeginAtAndSecurity(
                request.getFrom(),
                request.getSecurity());
    }

    @Transactional
    public Mono<SecuritiesCandles> save(Row row, RequestParamSecuritiesCandles request) {
        if (row == null || row.getBegin() == null || row.getEnd() == null || request.getSecurity() == null) {
            return Mono.just(new SecuritiesCandles());
        }
        var mapped = SecuritiesCandlesMapper.fromArrToEntity(row, request);
        return securitiesCandlesRepository.findByPk(
                        mapped.getSecuritiesCandlesPk().getSecId(),
                        mapped.getSecuritiesCandlesPk().getBeginAt(),
                        mapped.getSecuritiesCandlesPk().getEndAt())
                .switchIfEmpty(securitiesCandlesRepository.save(mapped))
                ;
    }

}
