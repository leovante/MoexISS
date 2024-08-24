package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.dao.SecuritiesCandlesDao;
import ru.exdata.moex.db.entity.SecuritiesCandlesAbstract;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Data;
import ru.exdata.moex.dto.candles.Document;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.handler.client.SecuritiesCandlesApiClient;
import ru.exdata.moex.handler.model.PageNumber;
import ru.exdata.moex.mapper.SecuritiesCandlesMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesHandler {

    private final SecuritiesCandlesApiClient securitiesApiClient;
    private final SecuritiesCandlesDao securitiesCandlesDao;
    private final HolidayService holidayService;
    private final int MOEX_RESPONSE_MAX_ROW = 500;

    public Flux<Row> fetch(RequestParamSecuritiesCandles request) {
        holidayService.weekendsIncrementFromOneDay(request);
        validateRequest(request);
        return request.getReverse()
                ? fetchRepoReverse(request)
                : fetchRepo(request);
    }

    /**
     * проверяет наличие записей в бд инкрементально по одному дню. Как только ничего не находит - переходит к запросам API
     *
     * @param request
     * @return
     */
    private Flux<Row> fetchRepo(RequestParamSecuritiesCandles request) {
        return Flux.defer(() ->
                        securitiesCandlesDao
                                .findAllByBeginAtAndSecurity(request.getFrom(), request.getInterval(), request.getSecurity())
                                .map(it -> SecuritiesCandlesMapper.fromEntityToDtoCandles((SecuritiesCandlesAbstract) it)))
                .switchIfEmpty(fetchAndSave(request))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                                    holidayService.incrementFromOneDay(request);
                                    holidayService.weekendsIncrementFromOneDay(request);
                                    return request.getFrom().isBefore(request.getTill()) || request.getFrom().isEqual(request.getTill());
                                }
                        )
                );
    }

    private Flux<Row> fetchRepoReverse(RequestParamSecuritiesCandles request) {
        return Flux.defer(() ->
                        securitiesCandlesDao
                                .findAllByBeginAtAndSecurity(request.getTill(), request.getInterval(), request.getSecurity())
                                .map(it -> SecuritiesCandlesMapper.fromEntityToDtoCandles((SecuritiesCandlesAbstract) it)))
                .switchIfEmpty(fetchAndSave(request))
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                                    holidayService.decrementTillOneDay(request);
                                    holidayService.weekendsDecrementTillOneDay(request);
                                    return request.getFrom().isBefore(request.getTill()) || request.getFrom().isEqual(request.getTill());
                                }
                        )
                );
    }

    private Flux<Row> fetchAndSave(RequestParamSecuritiesCandles request) {
        PageNumber pageNumber = new PageNumber();
        return Flux.defer(() -> fetchPageable(request, pageNumber)
                        .doOnNext(it -> securitiesCandlesDao.save(it, request).subscribe())
                )
                // добавить тут логику для реверс
                // добавить для pageNumber сохранять последнюю полученную дату
                // проверять последнюю дату локально перед запросом
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> pageNumber.get() > 0));
    }

    private Flux<Row> fetchPageable(RequestParamSecuritiesCandles request, PageNumber pageNumber) {
        return securitiesApiClient.fetch(
                        request.getSecurity(),
                        request.getFrom().toString(),
                        request.getTill().toString(),
                        String.valueOf(pageNumber.get()),
                        request.getInterval(),
                        request.getReverse())
                .filter(it -> !it.getData().getRows().isEmpty())
                .doOnNext(it -> {
                    if (!request.getReverse()) {
                        holidayService.saveMissingDatesCandlesBackground(it, request.getFrom(), request.getSecurity(), request.getBoard());
                    }
                })
                .doOnNext(it -> {
                    var rows = it.getData().getRows();
                    log.debug("response from securities candles moex api, first date: {}, last date: {}, batch rows size: {}",
                            rows.get(0).getBegin(),
                            rows.get(rows.size() - 1).getEnd(),
                            rows.size());
                    if (it.getData().getRows().size() < MOEX_RESPONSE_MAX_ROW) {
                        pageNumber.stop();
                        holidayService.alignDays(request);
                    } else {
                        pageNumber.increment(MOEX_RESPONSE_MAX_ROW);
                        log.debug("page number: " + pageNumber.get());
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    pageNumber.stop();
                    holidayService.alignDays(request);
                    return Mono.empty();
                }))
                .flatMapIterable(it -> Optional.of(it)
                        .map(Document::getData)
                        .map(Data::getRows)
                        .orElse(Collections.emptyList()))
                ;
    }

    private void validateRequest(RequestParamSecuritiesCandles request) {
        if (request.getFrom() != null && request.getFrom().isBefore(LocalDate.ofYearDay(2000, 1))) {
            throw new RuntimeException("Ошибка валидатора запроса. значение from не должно быть before 2000-01-01");
        }
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
        if (!Set.of(1, 10, 60, 24, 7, 31).contains(request.getInterval())) {
            throw new RuntimeException("Ошибка валидатора запроса. Interval should be eq = 1,10,60,24,7,31");
        }
    }

}
