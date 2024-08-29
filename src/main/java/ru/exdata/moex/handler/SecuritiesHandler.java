package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.dao.SecuritiesDao;
import ru.exdata.moex.dto.RequestParamSecurities;
import ru.exdata.moex.dto.securities.Row;
import ru.exdata.moex.handler.client.SecuritiesApiClient;
import ru.exdata.moex.handler.model.PageNumber;

import java.util.List;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesHandler {

    private final SecuritiesApiClient securitiesApiClient;
    private final SecuritiesDao securitiesDao;
    private final int MOEX_RESPONSE_MAX_ROW = 100;
//    private final EmitterProcessor<Boolean> shutdown = EmitterProcessor.create();

    public Flux<Row> fetch(RequestParamSecurities request) {
        validateRequest(request);
        return fetchSecurities(request)
                .doOnNext(it -> securitiesDao.save(it).subscribe());
    }

    private Flux<Row> fetchSecurities(RequestParamSecurities request) {
        final PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> fetchPageable(request, pageNumber))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> pageNumber.get() >= 0));
    }

    private Flux<Row> fetchPageable(RequestParamSecurities request, PageNumber pageNumber) {
        return securitiesApiClient.fetch(request, pageNumber.get())
                .doOnError(e -> new Exception(e.getMessage()))
                .filter(it -> !it.getData().getRows().isEmpty())
                .doOnNext(c -> log.debug("request to securities moex api: " + c.getData().getRows().get(0).getSecId()))
                .map(it -> {
                    if (request.getLimit() <= MOEX_RESPONSE_MAX_ROW) {
                        pageNumber.increment(request.getLimit());
                        log.debug("page number: " + pageNumber.get());
                    } else {
                        pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                        log.debug("page number: " + pageNumber.get());
                    }
                    return it;
                })
//                .takeUntilOther(shutdown)
                .switchIfEmpty(Mono.defer(() -> {
                    pageNumber.stop();
                    return Mono.never();
                }))
                .flatMapIterable(it -> it.getData().getRows())
                ;
    }

    private void validateRequest(RequestParamSecurities request) {
        if (!List.of(5, 10, 20, 100).contains(request.getLimit())) {
            throw new RuntimeException("Ошибка валидатора запроса. limit должен быть = 5, 10, 20, 100");
        }
        if (request.getStart() < 0) {
            throw new RuntimeException("Ошибка валидатора запроса. start должен быть > 0");
        }
        if (request.getQ() != null && request.getQ().length() < 4) {
            throw new RuntimeException("Ошибка валидатора запроса. длина Q должна быть >= 3");
        }
        if (!request.getLang().equals("ru")) {
            throw new RuntimeException("Ошибка валидатора запроса. lang = ru");
        }
    }

}
