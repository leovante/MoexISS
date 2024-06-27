package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesHoliday;
import ru.exdata.moex.db.repository.SecuritiesHolidayRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Singleton
public class SecuritiesHolidayDao {

    private final SecuritiesHolidayRepository securitiesHolidayRepository;

    @Transactional
    public Mono<SecuritiesHoliday> findByBoardIdAndTradeDateAndSecId(String boardId, LocalDate tradeDate, String secId) {
        return securitiesHolidayRepository.findByBoardIdAndTradeDateAndSecId(boardId, tradeDate, secId);
    }

    @Transactional
    public Mono<SecuritiesHoliday> save(SecuritiesHoliday entity) {
        if (entity == null) {
            return Mono.just(new SecuritiesHoliday());
        }
        return securitiesHolidayRepository
                .findByBoardIdAndTradeDateAndSecId(
                        entity.getBoardId(),
                        entity.getTradeDate(),
                        entity.getSecId())
                .switchIfEmpty(securitiesHolidayRepository.save(entity));
    }

}
