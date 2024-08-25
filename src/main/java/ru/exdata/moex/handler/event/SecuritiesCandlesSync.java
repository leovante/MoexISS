package ru.exdata.moex.handler.event;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.config.properties.ApplicationConfigAdapter;
import ru.exdata.moex.db.dao.MoexSyncDao;
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.enums.Interval;
import ru.exdata.moex.handler.SecuritiesCandlesHandler;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;
import ru.exdata.moex.handler.event.lock.SecuritiesCandlesSyncLock;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;
import ru.exdata.moex.utils.lock.LockService;
import ru.exdata.moex.utils.lock.LockServiceHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesSync implements LockServiceHelper {

    private static final String CATEGORY = String.format("%s", SecuritiesCandlesSync.class.getSimpleName());
    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;
    private final SecuritiesCandlesHandler securitiesCandlesHandler;
    private final LockService lockService;
    private final ApplicationConfigAdapter applicationConfigAdapter;
    private final MoexSyncDao moexSyncDao;

    public void sync() {
        log.info("Startup sync securities candles START");
        Scheduler scheduler1 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync1");
        Scheduler scheduler2 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync2");
        Scheduler scheduler3 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync3");
        Scheduler scheduler4 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync4");
        Scheduler scheduler5 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync5");
        Scheduler scheduler6 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync6");

        Disposable disposable = null;
        for (Integer listing : List.of(1, 2, 3)) {
            disposable = specificationsSecuritiesHandler.fetchByListingLvl(listing)
                    .flatMap(sec -> Mono.just(sec.sec_id())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M1, sec.sec_id()))
                                    .subscribeOn(scheduler1)
                                    .subscribe())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M10, sec.sec_id()))
                                    .subscribeOn(scheduler2)
                                    .subscribe())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M60, sec.sec_id()))
                                    .subscribeOn(scheduler3)
                                    .subscribe())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D1, sec.sec_id()))
                                    .subscribeOn(scheduler4)
                                    .subscribe())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D7, sec.sec_id()))
                                    .subscribeOn(scheduler5)
                                    .subscribe())
                            .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D31, sec.sec_id()))
                                    .subscribeOn(scheduler6)
                                    .subscribe())
                    )
                    .subscribe();
        }

        while (true) {
            if (disposable.isDisposed()) {
                log.info("Startup sync securities candles FINISH");
                moexSyncDao.save(MoexSync.builder().dataType(SyncTypes.candles.name()).build())
                        .subscribe();
                break;
            }
        }

    }

    @SneakyThrows
    private Object syncProcess(SupplierMethodInvocation supplierMethodInvocation, Interval interval) {
        var lockName = String.format(CATEGORY.concat("-%s"), interval);
        return invokeWithLock(lockService, new SecuritiesCandlesSyncLock(lockName),
                applicationConfigAdapter.resourceLockTimeout(),
                supplierMethodInvocation);
    }

    @SneakyThrows
    private RequestParamSecuritiesCandles createRequest(Interval interval, String sec) {
        var now = LocalDate.now();
        var intervalToMoexMap = Map.of(
                Interval.M1, 1,
                Interval.M10, 3,
                Interval.M60, 3,
                Interval.D1, 12 * 5,
                Interval.D7, 12 * 5,
                Interval.D31, 12 * 20);

        var lastSync = moexSyncDao.findByDataTypeOrderByUpdateDateDesc(SyncTypes.candles).toFuture().get();
        var fromActual = lastSync == null
                ? now.minusMonths(intervalToMoexMap.get(interval))
                : lastSync.getUpdateDate().toLocalDate().plusDays(1L);

        return RequestParamSecuritiesCandles.builder()
                .security(sec)
                .board(null)
                .from(fromActual)
                .till(now)
                .interval(interval.value)
                .reverse(false)
                .build();
    }

}
