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
import ru.exdata.moex.enums.Board;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
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

    void saveMissingDatesCandlesBackground(Document dto, LocalDate start, String secId, String board) {
        String boardId = board == null ? Board.TQBR.name() : board;
        Flux.just(dto)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .doOnNext(it ->
                        Optional.ofNullable(dto.getData().getRows())
                                .map(map -> map.get(0))
                                .ifPresent(firstDto -> saveMissingDates(
                                        start,
                                        firstDto.getBegin().toLocalDate(),
                                        LocalDate::isEqual, dataToSave -> {
                                            var entity = new SecuritiesHoliday();
                                            entity.setSecId(secId);
                                            entity.setBoardId(boardId);
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
                                        entity.setBoardId(boardId);
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
                .map(dataToSave -> securitiesHolidayDao.save(entity.apply(dataToSave)).subscribe())
                .toList();
    }

    void alignDays(GeneralRequest request) {
        request.setFrom(request.getTill());
    }

    void incrementDay(AtomicReference<LocalDate> from) {
        if (LocalDate.now().isAfter(from.get())) {
            from.set(from.get().plusDays(1L));
        }
    }

    void incrementFromOneDay(GeneralRequest request) {
        if (request.getFrom().isBefore(request.getTill()) && LocalDate.now().isAfter(request.getFrom())) {
            request.setFrom(request.getFrom().plusDays(1L));
        }
    }

    void decrementTillOneDay(GeneralRequest request) {
        request.setTill(request.getTill().minusDays(1L));
    }

    void weekendsIncrementFromOneDay(GeneralRequest request, AtomicReference<LocalDate> from) {
        while (isWeekends(from.get()) || hasHolidayGap(request.getBoard(), request.getSecurity(), from.get())) {
            if (from.get().isBefore(request.getTill()) && LocalDate.now().isAfter(from.get())) {
                from.set(from.get().plusDays(1L));
            } else {
                break;
            }
        }
    }

    void weekendsIncrementFromOneDay(GeneralRequest request) {
        while (isWeekends(request.getFrom()) || hasHolidayGap(request.getBoard(), request.getSecurity(), request.getFrom())) {
            if (request.getFrom().isBefore(request.getTill()) && LocalDate.now().isAfter(request.getFrom())) {
                request.setFrom(request.getFrom().plusDays(1L));
            } else {
                break;
            }
        }
    }

    void weekendsDecrementTillOneDay(GeneralRequest request) {
        while (isWeekends(request.getTill()) || hasHolidayGap(request.getBoard(), request.getSecurity(), request.getTill())) {
            request.setTill(request.getTill().minusDays(1L));
        }
    }

    public boolean isHoliday(LocalDate date, String board, String security) {
        return isWeekends(date) || hasHolidayGap(board, security, date);
    }

    private boolean isWeekends(LocalDate date) {
        var weekField = WeekFields.of(Locale.ROOT);
        date.with(weekField.dayOfWeek(), 1);
        return date.getDayOfWeek().getValue() == 6
                || date.getDayOfWeek().getValue() == 7;
    }

    private boolean hasHolidayGap(String board, String security, LocalDate date) {
        return Boolean.TRUE.equals(securitiesHolidayDao.findByBoardIdAndTradeDateAndSecId(
                        board == null ? Board.TQBR.name() : board,
                        date,
                        security)
                .hasElement().subscribe());
    }

}
