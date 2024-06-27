package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesHistoryDao;
import ru.exdata.moex.db.dao.SecuritiesHolidayDao;
import ru.exdata.moex.db.entity.SecuritiesHoliday;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.dto.history.SecuritiesHistoryDto;
import ru.exdata.moex.handler.client.SecuritiesHistoryApiClient;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesHistoryHandler {

    private final SecuritiesHistoryApiClient securitiesHistoryApiClient;
    private final SecuritiesHistoryDao securitiesHistoryDao;
    private final SecuritiesHolidayDao securitiesHolidayDao;
    private final int MOEX_RESPONSE_MAX_ROW = 100;


    public Flux<Object[]> fetch(RequestParamSecuritiesHistory request) {
        AtomicReference<LocalDate> fromDate = new AtomicReference<>(request.getFrom());
        weekendsIncrement(request, fromDate);
        validateRequest(request);
        return fetchRepo(request, fromDate);
    }

    /**
     * https://projectreactor.io/docs/core/release/reference/#_retrying.
     *
     * @param request request.
     * @return ret.
     */
    private Flux<Object[]> fetchRepo(RequestParamSecuritiesHistory request, AtomicReference<LocalDate> fromDate) {
        return Flux.defer(() -> securitiesHistoryDao.findByTradeDateAndBoardIdAndSecId(request, fromDate.get()))
                .switchIfEmpty(fetchAndSave(request, fromDate))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                                    incrementDay(fromDate);
                                    weekendsIncrement(request, fromDate);
                                    return (fromDate.get().isBefore(request.getTill()) || fromDate.get().isEqual(request.getTill()))
                                            && fromDate.get().isBefore(LocalDate.now());
                                }
                        )
                );
    }

    private Flux<Object[]> fetchAndSave(RequestParamSecuritiesHistory request, AtomicReference<LocalDate> fromDate) {
        PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> fetchPageable(request, pageNumber, fromDate)
                        .parallel()
                        .runOn(Schedulers.boundedElastic())
                        .doOnNext(it -> securitiesHistoryDao.save(it).subscribe())
                        .sequential()
                )
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> pageNumber.get() > 0))
                ;
    }

    private Flux<Object[]> fetchPageable(RequestParamSecuritiesHistory request, PageNumber pageNumber, AtomicReference<LocalDate> fromDate) {
        return securitiesHistoryApiClient.fetch(
                        request.getSecurity(),
                        fromDate.get().toString(),
                        request.getTill().toString(),
                        null)
                .filter(it -> it.getSecuritiesHistory().getColumns().length == 23)
                .filter(it -> !it.getSecuritiesHistory().getData().isEmpty())
                .doOnNext(it -> saveMissingDatesBackground(it, fromDate.get()))
                .doOnNext(it -> {
                    var firstName = it.getSecuritiesHistory().getData();
                    log.debug("SecuritiesHistory: " + firstName.get(0)[1]);
                    if (request.getDuration().toDays() == 0 || it.getSecuritiesHistory().getData().size() == request.getDuration().toDays()) {
                        pageNumber.stop();
                        alignDays(request, fromDate);
                    } else if (it.getSecuritiesHistory().getData().size() < MOEX_RESPONSE_MAX_ROW) {
//                        pageNumber.increment(it.getSecuritiesHistory().getData().size());
                        pageNumber.stop();
                        alignDays(request, fromDate);
                    } else {
                        var linked = new LinkedList<Object[]>(it.getSecuritiesHistory().getData());
                        var mapped = SecuritiesHistoryMapper.fromArrToEntity(linked.getLast());
                        incrementDay(fromDate, mapped.getTradeDate().plusDays(1L));
                        pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                        log.debug("pageNumber: " + pageNumber.get());
                    }
                })
                .flatMapIterable(it -> it.getSecuritiesHistory().getData());
    }

    private class PageNumber {
        private int page = 0;

        void increment(int i) {
            page = page + i;
        }

        public int get() {
            return page;
        }

        public void stop() {
            page = -1;
        }
    }

    private void saveMissingDatesBackground(SecuritiesHistoryDto dto, LocalDate fromDate) {
        Flux.just(dto)
                .parallel(3)
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

    private void alignDays(RequestParamSecuritiesHistory request, AtomicReference<LocalDate> fromDate) {
        fromDate.set(request.getTill());
    }

    private void incrementDay(AtomicReference<LocalDate> request, LocalDate date) {
        request.set(date);
    }

    private void incrementDay(AtomicReference<LocalDate> from) {
        from.set(from.get().plusDays(1L));
    }

    private void weekendsIncrement(RequestParamSecuritiesHistory request, AtomicReference<LocalDate> from) {
        while (isWeekends(from.get()) || hasHolidayGap(request, from)) {
            from.set(from.get().plusDays(1L));
        }
    }

    private boolean isWeekends(LocalDate date) {
        return date.getDayOfWeek().getValue() == 6
                || date.getDayOfWeek().getValue() == 7;
    }

    private boolean hasHolidayGap(RequestParamSecuritiesHistory request, AtomicReference<LocalDate> from) {
        return Boolean.TRUE.equals(securitiesHolidayDao.findByBoardIdAndTradeDateAndSecId(
                        request.getBoard(),
                        from.get(),
                        request.getSecurity())
                .hasElement().subscribe());
    }

    private void validateRequest(RequestParamSecuritiesHistory request) {
        if (request.getFrom() == null) {
            throw new RuntimeException("Ошибка валидатора запроса. значение from не должно быть null {Например: 2024-06-25}");
        }
        if (request.getTill() == null) {
            throw new RuntimeException("Ошибка валидатора запроса. значение till не должно быть null {Например: 2024-06-25}");
        }
        if (request.getSecurity() == null) {
            throw new RuntimeException("Ошибка валидатора запроса. значение security не должно быть null {Например: SBER}");
        }
        if (request.getSecurity().length() != 4) {
            throw new RuntimeException("Ошибка валидатора запроса. длина security должна быть = 4");
        }
        if (request.getTill().isAfter(LocalDate.now())) {
            throw new RuntimeException("Ошибка валидатора запроса. till should be before now");
        }
        /*if (Duration.between(request.getFrom(), request.getTill()).toDays() <= 3 * 365) {
            throw new RuntimeException("Ошибка валидатора запроса. Не более 3-х лет");
        }*/
    }

}
