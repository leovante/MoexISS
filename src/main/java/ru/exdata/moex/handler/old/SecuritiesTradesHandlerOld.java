package ru.exdata.moex.handler.old;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesTradesDao;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;
import ru.exdata.moex.handler.client.SecuritiesTradesApiClient;
import ru.exdata.moex.mapper.SecuritiesTradesMapper;

import java.time.LocalDate;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesTradesHandlerOld {

    /*private final SecuritiesTradesApiClient securitiesTradesApiClient;
    private final SecuritiesTradesDao securitiesTradesDao;
    private final int MOEX_RESPONSE_MAX_ROW = 5000;

    public Flux<Object[]> fetch(RequestParamSecuritiesTrades request) {
        validateRequest(request);
        return Flux.defer(() ->
                        fetchDb(request)
                                .switchIfEmpty(
                                        fetchMoexByDateWithPagination(request)
                                                .publishOn(Schedulers.boundedElastic())
                                                .doOnNext(it -> securitiesTradesDao.save(it).subscribe())))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                            request.setFrom(request.getFrom().plusDays(1L));
                            return request.getFrom().isBefore(request.getTill())
                                    || request.getFrom().isEqual(request.getTill());
                        }
                ));
    }

    private Flux<Object[]> fetchDb(RequestParamSecuritiesTrades request) {
        return securitiesTradesDao.findBySysTime(request);
    }

    private Flux<Object[]> fetchMoexByDateWithPagination(RequestParamSecuritiesTrades request) {
        final PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> createGetRequest(request, pageNumber))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount ->
                        pageNumber.get() > 0
                ));
    }

    private Flux<Object[]> createGetRequest(RequestParamSecuritiesTrades request, PageNumber pageNumber) {
        return securitiesTradesApiClient.fetch(
                        request.getSecurity(),
                        pageNumber.get(),
                        String.valueOf(MOEX_RESPONSE_MAX_ROW))
                .doOnNext(it -> {
                    log.debug("pageNumber: " + pageNumber.get());
                    pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                })
                .flatMapIterable(it -> it.getSecuritiesTrades().getData())
                .filter(it -> {
                    if (it.length == 0) {
                        if (pageNumber.getCountElements() > 0) pageNumber.stop();
                        return false;
                    }
                    var time = it[9];
                    log.debug("createGetRequest: " + time);
                    var date = toLocalDate(time);
                    return !date.isAfter(request.getTill()) && !date.isBefore(request.getFrom());
                })
                .doOnNext(it -> {
                    pageNumber.incrementCountElements();
                    log.debug("count: " + pageNumber.getCountElements());
                });
    }

    private class PageNumber {
        private int page = 0;
        private int count = 0;

        void increment(int i) {
            page = page + i;
        }

        public int get() {
            return page;
        }

        public void stop() {
            page = 0;
        }

        void incrementCountElements() {
            count++;
        }

        public int getCountElements() {
            return count;
        }

    }

    private void validateRequest(RequestParamSecuritiesTrades request) {
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
        if (request.getFrom().isAfter(request.getTill())) {
            throw new RuntimeException("Ошибка валидатора запроса. from > till");
        }
    }

    private LocalDate toLocalDate(Object it) {
        return SecuritiesTradesMapper.mapFromObjectToLocalDate(it);
    }
*/
}
