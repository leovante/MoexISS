package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesHistory;
import ru.exdata.moex.db.repository.SecuritiesHistoryRepository;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesHistoryDao {

    private final SecuritiesHistoryRepository securitiesHistoryRepository;

    @Transactional
    public Mono<Object[]> findByTradeDateAndBoardIdAndSecId(RequestParamSecuritiesHistory request, LocalDate date) {
        log.info("fetch history from db. date:{} secId:{}", date, request.getSecurity());
        return securitiesHistoryRepository.findByTradeDateAndBoardIdAndSecId(
                        date,
                        request.getBoard(),
                        request.getSecurity())
                .map(SecuritiesHistoryMapper::fromEntityToArrSecuritiesTrades);
    }

    @Transactional
    public Mono<SecuritiesHistory> save(Object[] dtos) {
        if (dtos == null || dtos.length == 0) {
            return Mono.just(new SecuritiesHistory());
        }
        var mapped = SecuritiesHistoryMapper.fromArrToEntity(dtos);
        return securitiesHistoryRepository
                .findByTradeDateAndBoardIdAndSecIdAndValue(
                        mapped.getTradeDate(),
                        mapped.getBoardId(),
                        mapped.getSecId(),
                        mapped.getValue())
                .switchIfEmpty(securitiesHistoryRepository.save(mapped));
    }

}
