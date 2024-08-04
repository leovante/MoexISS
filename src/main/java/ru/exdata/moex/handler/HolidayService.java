package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesHolidayDao;
import ru.exdata.moex.db.entity.SecuritiesHoliday;
import ru.exdata.moex.dto.GeneralRequest;
import ru.exdata.moex.dto.candles.Document;
import ru.exdata.moex.dto.history.SecuritiesHistoryDto;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class HolidayService {

    private final SecuritiesHolidayDao securitiesHolidayDao;

    void saveMissingDatesHistoryBackground(SecuritiesHistoryDto dto, LocalDate fromDate) {
        Flux.just(dto)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .doOnNext(it ->
                        Optional.ofNullable(dto.getSecuritiesHistory().getData())
                                .map(map -> SecuritiesHistoryMapper.fromArrToEntity(map.get(0)))
                                .ifPresent(firstDto -> saveMissingDates(fromDate, firstDto.getTradeDate(), LocalDate::isEqual, dataToSave -> {
                                    var entity = new SecuritiesHoliday();
                                    entity.setSecId(firstDto.getSecId());
                                    entity.setBoardId(firstDto.getBoardId());
                                    entity.setTradeDate(dataToSave);
                                    return entity;
                                })))
                .doOnNext(it -> Flux.fromIterable(dto.getSecuritiesHistory().getData())
                        .map(SecuritiesHistoryMapper::fromArrToEntity)
                        .reduce((first, second) -> {
                            saveMissingDates(first.getTradeDate(), second.getTradeDate(), (excl, frst) -> !excl.isEqual(frst), dataToSave -> {
                                var entity = new SecuritiesHoliday();
                                entity.setSecId(first.getSecId());
                                entity.setBoardId(first.getBoardId());
                                entity.setTradeDate(dataToSave);
                                return entity;
                            });
                            return second;
                        }).subscribe()
                ).subscribe();
    }

    void saveMissingDatesCandlesBackground(Document dto, LocalDate from, String secId, String board) {
        Flux.just(dto)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .doOnNext(it ->
                        Optional.ofNullable(dto.getData().getRows())
                                .map(map -> map.get(0))
                                .ifPresent(firstDto -> saveMissingDates(
                                        from,
                                        firstDto.getBegin().toLocalDate(),
                                        LocalDate::isEqual, dataToSave -> {
                                            var entity = new SecuritiesHoliday();
                                            entity.setSecId(secId);
                                            entity.setBoardId(board);
                                            entity.setTradeDate(dataToSave);
                                            return entity;
                                        })))
                .doOnNext(it -> Flux.fromIterable(dto.getData().getRows())
                        .reduce((first, second) -> {
                            saveMissingDates(
                                    first.getBegin().toLocalDate(),
                                    second.getBegin().toLocalDate(),
                                    (excl, frst) -> !excl.isEqual(frst),
                                    dataToSave -> {
                                        var entity = new SecuritiesHoliday();
                                        entity.setSecId(secId);
                                        entity.setBoardId(board);
                                        entity.setTradeDate(dataToSave);
                                        return entity;
                                    });
                            return second;
                        }).subscribe()
                ).subscribe();
    }

    private void saveMissingDates(LocalDate firstDate,
                                  LocalDate secondDate,
                                  BiPredicate<LocalDate, LocalDate> firstInclude,
                                  Function<LocalDate, SecuritiesHoliday> entity) {
        firstDate.datesUntil(secondDate)
                .filter(excl -> firstInclude.test(firstDate, excl) && !excl.isEqual(secondDate))
                .map(dataToSave -> {
                    log.debug("Save holiday data: " + dataToSave);
                    return securitiesHolidayDao.save(entity.apply(dataToSave)).subscribe();
                })
                .toList();
    }

    void alignDays(LocalDate till, AtomicReference<LocalDate> fromDate) {
        fromDate.set(till);
    }

    void alignDays(GeneralRequest request) {
        request.setFrom(request.getTill());
    }

    void incrementDay(AtomicReference<LocalDate> request, LocalDate date) {
        request.set(date);
    }

    void incrementDay(AtomicReference<LocalDate> from) {
        from.set(from.get().plusDays(1L));
    }

    void incrementDay(GeneralRequest request) {
        request.setFrom(request.getFrom().plusDays(1L));
    }

    void weekendsIncrement(GeneralRequest request, AtomicReference<LocalDate> from) {
        while (isWeekends(from.get()) || hasHolidayGap(request, from.get())) {
            from.set(from.get().plusDays(1L));
        }
    }

    void weekendsIncrement(GeneralRequest request) {
        while (isWeekends(request.getFrom()) || hasHolidayGap(request, request.getFrom())) {
            request.setFrom(request.getFrom().plusDays(1L));
        }
    }

    private boolean isWeekends(LocalDate date) {
        return date.getDayOfWeek().getValue() == 6
                || date.getDayOfWeek().getValue() == 7;
    }

    private boolean hasHolidayGap(GeneralRequest request, LocalDate from) {
        return Boolean.TRUE.equals(securitiesHolidayDao.findByBoardIdAndTradeDateAndSecId(
                        request.getBoard() == null ? "TQBR" : request.getBoard(),
                        from,
                        request.getSecurity())
                .hasElement().subscribe());
    }

}
