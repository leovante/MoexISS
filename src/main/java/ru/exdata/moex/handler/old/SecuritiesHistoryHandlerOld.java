package ru.exdata.moex.handler.old;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesHistoryDao;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.dto.history.SecuritiesHistoryDto;
import ru.exdata.moex.handler.client.SecuritiesHistoryApiClient;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesHistoryHandlerOld {

    private final SecuritiesHistoryApiClient securitiesHistoryApiClient;
    private final SecuritiesHistoryDao securitiesHistoryDao;
    private final int MOEX_RESPONSE_MAX_ROW = 100;

    public Flux<Object[]> fetch(RequestParamSecuritiesHistory request) {
        validateRequest(request);
        return Flux.defer(() -> securitiesHistoryDao.findByTradeDateAndBoardIdAndSecId(request, request.getFrom()))
                .switchIfEmpty(fetchAndSave(request))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                            request.setFrom(request.getFrom().plusDays(1L));
                            return (request.getFrom().isBefore(request.getTill()) || request.getFrom().isEqual(request.getTill()))
                                    && request.getFrom().isBefore(LocalDate.now());
                        }
                ));
    }

    private Flux<Object[]> fetchAndSave(RequestParamSecuritiesHistory request) {
        PageNumber pageNumber = new PageNumber();
        return fetchPageable(request, pageNumber)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(it -> securitiesHistoryDao.save(it).subscribe())
                ;
    }

    private Flux<Object[]> fetchPageable(RequestParamSecuritiesHistory request, PageNumber pageNumber) {
        return securitiesHistoryApiClient.fetch(
                        request.getSecurity(),
                        request.getFrom().toString(),
                        request.getFrom().toString(),
                        String.valueOf(pageNumber.get()))
                .filter(it -> it.getSecuritiesHistory().getColumns().length == 23)
                .filter(it -> !it.getSecuritiesHistory().getData().isEmpty())
                .doOnNext(it -> {
                    var firstName = it.getSecuritiesHistory().getData();
                    log.debug("SecuritiesHistory: " + firstName.get(0)[1]);
                    if (it.getSecuritiesHistory().getData().size() < MOEX_RESPONSE_MAX_ROW) {
                        pageNumber.increment(it.getSecuritiesHistory().getData().size() + 1);
                    } else {
                        pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                        log.debug("pageNumber: " + pageNumber.get());
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    pageNumber.stop();
                    return Mono.fromCallable(SecuritiesHistoryDto::new).delayElement(Duration.ofSeconds(10L));
                }))
                .flatMapIterable(it -> it.getSecuritiesHistory() == null ? Collections.singleton(new Object[0]) : it.getSecuritiesHistory().getData())
                ;
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
