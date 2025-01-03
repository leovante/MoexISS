package ru.exdata.moex.handler;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import ru.exdata.moex.db.dao.SecuritiesCandlesDao;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.handler.client.SecuritiesCandlesApiClient;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@Singleton
@Named("SecuritiesCandlesSyncHandler")
public class SecuritiesCandlesSyncHandler extends SecuritiesCandlesHandler {

    public SecuritiesCandlesSyncHandler(SecuritiesCandlesApiClient securitiesApiClient,
                                        SecuritiesCandlesDao securitiesCandlesDao,
                                        HolidayService holidayService) {
        super(securitiesApiClient, securitiesCandlesDao, holidayService);
    }

    public Flux<Row> fetch(RequestParamSecuritiesCandles request) {
        validateRequest(request);
        if (holidayService.isHoliday(request.getFrom(), request.getBoard(), request.getSecurity())) {
            return Flux.empty();
        } else {
            holidayService.weekendsIncrementFromOneDay(request);
        }
        return fetchRepo(request);
    }

    @SneakyThrows
    @Transactional
    public Flux<Row> fetchRepo(RequestParamSecuritiesCandles request) {
        return Flux.defer(() ->
                        securitiesCandlesDao.countByBeginAtAndSecurity(
                                        request.getFrom(), request.getInterval(), request.getSecurity())
                                .flux()
                                .filter(it -> it == 0)
                                .flatMap(it -> fetchWebClientAndSave(request))
                )
                .doOnError(Flux::error)
                .repeatWhen(transactions -> transactions.takeWhile(transactionCount -> {
                    if (!request.getFrom().isBefore(request.getTill())) {
                        return false;
                    }
                    holidayService.incrementFromOneDay(request);
                    holidayService.weekendsIncrementFromOneDay(request);
                    return true;
                }));
    }

}
