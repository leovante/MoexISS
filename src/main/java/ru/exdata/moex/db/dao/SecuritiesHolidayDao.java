package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.SecuritiesHoliday;
import ru.exdata.moex.db.repository.SecuritiesHolidayRepository;
import ru.exdata.moex.enums.Board;
import ru.exdata.moex.utils.Routines;

import java.time.LocalDate;

@Slf4j
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
        log.info("save SecuritiesHoliday to db. security: {} data: {}",
                Routines.ifNotNull(entity, SecuritiesHoliday::getSecId),
                Routines.ifNotNull(entity, SecuritiesHoliday::getTradeDate));

        if (entity == null) {
            return Mono.empty();
        }

        return securitiesHolidayRepository
                .findByBoardIdAndTradeDateAndSecId(
                        entity.getBoardId() == null ? Board.TQBR.name() : entity.getBoardId(),
                        entity.getTradeDate(),
                        entity.getSecId())
                .switchIfEmpty(securitiesHolidayRepository.save(entity));
    }

}
