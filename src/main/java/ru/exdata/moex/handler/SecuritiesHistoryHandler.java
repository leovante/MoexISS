package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesHistoryDao;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.handler.client.SecuritiesHistoryApiClient;
import ru.exdata.moex.handler.model.PageNumber;
import ru.exdata.moex.mapper.SecuritiesHistoryMapper;

import java.time.LocalDate;
import java.util.LinkedList;

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
        validateRequest(request);
        holidayService.weekendsIncrementFromOneDay(request);
        return fetchRepo(request);
    }

    /**
     * https://projectreactor.io/docs/core/release/reference/#_retrying.
     *
     * @param request request.
     * @return ret.
     */
    private Flux<Object[]> fetchRepo(RequestParamSecuritiesHistory request) {
        return Flux.defer(() -> securitiesHistoryDao.findByTradeDateAndBoardIdAndSecId(request, request.getFrom()))
                .switchIfEmpty(fetchAndSave(request))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                                    if (!request.getFrom().isBefore(request.getTill())) {
                                        return false;
                                    }
                                    holidayService.incrementDay(request.getAtomicFrom());
                                    holidayService.weekendsIncrementFromOneDay(request, request.getAtomicFrom());
                                    return true;
                                }
                        )
                );
    }

    private Flux<Object[]> fetchAndSave(RequestParamSecuritiesHistory request) {
        PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> fetchPageable(request, pageNumber)
                        .parallel()
                        .runOn(Schedulers.boundedElastic())
                        .doOnNext(it -> securitiesHistoryDao.save(it).subscribe())
                        .sequential()
                )
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> pageNumber.get() > 0))
                ;
    }

    private Flux<Object[]> fetchPageable(RequestParamSecuritiesHistory request, PageNumber pageNumber) {
        return securitiesHistoryApiClient.fetch(request)
//                .doOnNext(it -> holidayService.saveMissingDatesHistoryBackground(it, fromDate.get()))
                .flatMap(response -> Mono.just(response)
                        .doOnError(e -> new Exception(e.getMessage()))
                        .filter(it -> it.getSecuritiesHistory() != null)
                        .filter(it -> it.getSecuritiesHistory().getColumns().length == 23)
                        .filter(it -> !it.getSecuritiesHistory().getData().isEmpty())
                        .map(it -> {
                            var firstName = response.getSecuritiesHistory().getData();
                            log.debug("request to securities history moex api: " + firstName.get(0)[1]);
                            if (request.getDuration().toDays() == 0 ||
                                    response.getSecuritiesHistory().getData().size() == request.getDuration().toDays()) {
                                pageNumber.stop();
                                holidayService.alignDays(request);
                            } else if (response.getSecuritiesHistory().getData().size() < MOEX_RESPONSE_MAX_ROW) {
                                pageNumber.stop();
                                holidayService.alignDays(request);
                            } else {
                                var linked = new LinkedList<Object[]>(response.getSecuritiesHistory().getData());
                                var mapped = SecuritiesHistoryMapper.fromArrToEntity(linked.getLast());
                                request.setFrom(mapped.getTradeDate().plusDays(1L));
                                pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                                log.debug("page number: " + pageNumber.get());
                            }
                            return response;
                        }))
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
    }

}
