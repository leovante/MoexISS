package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.SecuritiesTradesDao;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;
import ru.exdata.moex.dto.trades.SecuritiesTrades;
import ru.exdata.moex.dto.trades.SecuritiesTradesDto;
import ru.exdata.moex.handler.client.SecuritiesTradesApiClient;
import ru.exdata.moex.handler.model.PageNumber;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesTradesHandler {

    private final SecuritiesTradesApiClient securitiesApiClient;
    private final SecuritiesTradesDao securitiesTradesDao;
    private final int MOEX_RESPONSE_MAX_ROW = 5000;

    public Flux<Object[]> fetch(RequestParamSecuritiesTrades request) {
        validateRequest(request);
        return securitiesTradesDao.findLast(request)
                .doOnNext(it -> request.setTradeno(it.getTradeNo()))
                .thenMany(fetchSecurities(request))
                ;
    }

    private Flux<Object[]> fetchSecurities(RequestParamSecuritiesTrades request) {
        final PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> fetchPageable(request, pageNumber)
                        .publishOn(Schedulers.boundedElastic())
                        .doOnNext(it -> securitiesTradesDao.save(it).subscribe()))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                    request.setTradeno(0);
                    return pageNumber.get() >= 0;
                }));
    }

    private Flux<Object[]> fetchPageable(RequestParamSecuritiesTrades request, PageNumber pageNumber) {
        return securitiesApiClient.fetch(
                        request.getSecurity(),
                        request.getEngine(),
                        request.getMarket(),
                        String.valueOf(request.getTradeno()),
                        String.valueOf(pageNumber.get() + request.getStart()),
                        String.valueOf(MOEX_RESPONSE_MAX_ROW))
                .filter(it -> !it.getSecuritiesTrades().getData().isEmpty())
                .filter(dataFilter())
                .doOnNext(pagePaginator(request, pageNumber))
                .switchIfEmpty(Mono.defer(() ->
                        Mono.fromCallable(() ->
                                        new SecuritiesTradesDto(new SecuritiesTrades(
                                                null,
                                                Collections.singletonList(new Object[0]),
                                                null)))
                                .delayElement(Duration.ofSeconds(10L))))
                .flatMapIterable(it -> it.getSecuritiesTrades().getData())
                ;
    }

    private Consumer<SecuritiesTradesDto> pagePaginator(RequestParamSecuritiesTrades request, PageNumber pageNumber) {
        return it -> {
            var firstName = it.getSecuritiesTrades().getData();
            log.debug("request to securities trades moex api: " + firstName.get(0)[1]);
            if (it.getSecuritiesTrades().getData().size() < MOEX_RESPONSE_MAX_ROW) {
                pageNumber.increment(it.getSecuritiesTrades().getData().size());
            } else {
                pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                log.debug("page number: " + pageNumber.get());
            }
        };
    }

    private Predicate<SecuritiesTradesDto> dataFilter() {
        return it -> {
            if (it.getSecuritiesTrades().getDataversion() == null) {
                return true;
            } else if (it.getSecuritiesTrades().getDataversion().getData().contains(8154)) {
                return true;
            } else {
                log.debug("api version changed: " + it.getSecuritiesTrades().getDataversion().getData());
                return false;
            }
        };
    }

    private void validateRequest(RequestParamSecuritiesTrades request) {
        /*if (!List.of(1, 10, 100, 1000, 5000).contains(request.getLimit())) {
            throw new RuntimeException("Ошибка валидатора запроса. limit должен быть = 1, 10, 100, 1000, 5000");
        }*/
        if (request.getStart() < 0) {
            throw new RuntimeException("Ошибка валидатора запроса. start должен быть > 0");
        }
        /*if (request.getLimit() != MOEX_RESPONSE_MAX_ROW) {
            throw new RuntimeException("Ошибка валидатора запроса. limit == " + MOEX_RESPONSE_MAX_ROW);
        }*/
    }

}
