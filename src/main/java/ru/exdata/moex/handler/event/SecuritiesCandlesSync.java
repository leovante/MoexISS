package ru.exdata.moex.handler.event;

import io.micronaut.transaction.TransactionOperations;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.exdata.moex.config.properties.ApplicationConfigAdapter;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.enums.Interval;
import ru.exdata.moex.handler.SecuritiesCandlesHandler;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;
import ru.exdata.moex.handler.event.lock.SecuritiesCandlesSyncLock;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;
import ru.exdata.moex.utils.lock.LockService;
import ru.exdata.moex.utils.lock.LockServiceHelper;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesSync implements LockServiceHelper {

    private static final String CATEGORY = String.format("%s", SecuritiesCandlesSync.class.getSimpleName());
    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;
    private final SecuritiesCandlesHandler securitiesCandlesHandler;
    private final LockService lockService;
    //    private final ApplicationConfigurationProperties configurationProperties;
    private final ApplicationConfigAdapter applicationConfigAdapter;
    private final TransactionOperations<Object> transactionOperations;

    public void sync() {
        log.info("Startup sync securities candles START");

        for (Integer lvl : List.of(1, 2, 3)) {
            specificationsSecuritiesHandler.fetchByListingLvl(lvl)
                    .doOnNext(it -> syncProcess(() ->
                            securitiesCandlesHandler.fetch(
                                    createRequest(Interval.M1, it.sec_id())), Interval.M1))
//                    .doOnNext(it -> securitiesCandlesHandler.fetch(createRequest(Interval.M10, it.sec_id())).subscribe())
//                    .doOnNext(it -> securitiesCandlesHandler.fetch(createRequest(Interval.M60, it.sec_id())).subscribe())
//                    .doOnNext(it -> securitiesCandlesHandler.fetch(createRequest(Interval.D1, it.sec_id())).subscribe())
//                    .doOnNext(it -> securitiesCandlesHandler.fetch(createRequest(Interval.D7, it.sec_id())).subscribe())
//                    .doOnNext(it -> securitiesCandlesHandler.fetch(createRequest(Interval.D31, it.sec_id())).subscribe())
                    .subscribe(i -> log.debug("Startup sync securities candles FINISH"));
        }
    }

    @SneakyThrows
    private void syncProcess(SupplierMethodInvocation<Row, Exception> supplierMethodInvocation, Interval interval) {
        var lockName = String.format(CATEGORY.concat("-%s"), interval);
        invokeWithLock(lockService, new SecuritiesCandlesSyncLock(lockName),
                applicationConfigAdapter.resourceLockTimeout(),
                supplierMethodInvocation);
    }

    private RequestParamSecuritiesCandles createRequest(Interval interval, String sec) {
        return switch (interval) {
            case M1 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(1),
                    LocalDate.now(),
                    interval.value,
                    false);
            case M10 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(3),
                    LocalDate.now(),
                    interval.value,
                    false);
            case M60 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(3),
                    LocalDate.now(),
                    interval.value,
                    false);
            case D1 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(12),
                    LocalDate.now(),
                    interval.value,
                    false);
            case D7 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(12),
                    LocalDate.now(),
                    interval.value,
                    false);
            case D31 -> new RequestParamSecuritiesCandles(
                    sec,
                    null,
                    LocalDate.now().minusMonths(12 * 20),
                    LocalDate.now(),
                    interval.value,
                    false);
        };
    }

}
