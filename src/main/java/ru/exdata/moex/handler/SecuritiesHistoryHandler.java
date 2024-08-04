package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesHistoryDao;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.handler.client.SecuritiesHistoryApiClient;
import ru.exdata.moex.handler.model.PageNumber;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesHistoryHandler {

    private final SecuritiesHistoryApiClient securitiesHistoryApiClient;
    private final SecuritiesHistoryDao securitiesHistoryDao;
    private final HolidayService holidayService;

    private final int MOEX_RESPONSE_MAX_ROW = 100;


    public Flux<Object[]> fetch(RequestParamSecuritiesHistory request) {
        AtomicReference<LocalDate> fromDate = new AtomicReference<>(request.getFrom());
        holidayService.weekendsIncrement(request, fromDate);
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
                                    holidayService.incrementDay(fromDate);
                                    holidayService.weekendsIncrement(request, fromDate);
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
                .doOnNext(it -> holidayService.saveMissingDatesHistoryBackground(it, fromDate.get()))
                .doOnNext(it -> {
                    var firstName = it.getSecuritiesHistory().getData();
                    log.debug("request to securities history moex api: " + firstName.get(0)[1]);
                    if (request.getDuration().toDays() == 0 || it.getSecuritiesHistory().getData().size() == request.getDuration().toDays()) {
                        pageNumber.stop();
                        holidayService.alignDays(request.getTill(), fromDate);
                    } else if (it.getSecuritiesHistory().getData().size() < MOEX_RESPONSE_MAX_ROW) {
//                        pageNumber.increment(it.getSecuritiesHistory().getData().size());
                        pageNumber.stop();
                        holidayService.alignDays(request.getTill(), fromDate);
                    } else {
                        var linked = new LinkedList<Object[]>(it.getSecuritiesHistory().getData());
                        var mapped = SecuritiesHistoryMapper.fromArrToEntity(linked.getLast());
                        holidayService.incrementDay(fromDate, mapped.getTradeDate().plusDays(1L));
                        pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                        log.debug("page number: " + pageNumber.get());
                    }
                })
                .flatMapIterable(it -> it.getSecuritiesHistory().getData());
    }


    private void validateRequest(RequestParamSecuritiesHistory request) {
        if (request.getFrom() == null) {
            throw new RuntimeException("Ошибка валидатора запроса. значение from не должно быть null {Например: 2024-06-25}");
        }
        if (request.getTill() != null && request.getTill().isAfter(LocalDate.now())) {
            throw new RuntimeException("Ошибка валидатора запроса. till should be before now");
        }
        if (request.getTill() == null) {
            request.setTill(LocalDate.now());
        }
        if (request.getSecurity() == null) {
            throw new RuntimeException("Ошибка валидатора запроса. значение security не должно быть null {Например: SBER}");
        }
        if (request.getSecurity().length() < 4 || request.getSecurity().length() > 5) {
            throw new RuntimeException("Ошибка валидатора запроса. длина security должна быть = 4 или 5 символов");
        }
        /*if (Duration.between(request.getFrom(), request.getTill()).toDays() <= 3 * 365) {
            throw new RuntimeException("Ошибка валидатора запроса. Не более 3-х лет");
        }*/
    }

}
