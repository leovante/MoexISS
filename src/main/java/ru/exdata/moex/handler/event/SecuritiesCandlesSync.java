package ru.exdata.moex.handler.event;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.config.properties.ApplicationConfigAdapter;
import ru.exdata.moex.db.dao.MoexSyncDao;
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.enums.Interval;
import ru.exdata.moex.handler.HolidayService;
import ru.exdata.moex.handler.SecuritiesCandlesSyncHandler;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;
import ru.exdata.moex.handler.event.lock.SecuritiesCandlesSyncLock;
import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;
import ru.exdata.moex.utils.lock.LockService;
import ru.exdata.moex.utils.lock.LockServiceHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesSync implements LockServiceHelper {

    private static final String CATEGORY = String.format("%s", SecuritiesCandlesSync.class.getSimpleName());
    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;
    @Named("SecuritiesCandlesSyncHandler")
    @Inject
    private SecuritiesCandlesSyncHandler securitiesCandlesHandler;
    private final LockService lockService;
    private final ApplicationConfigAdapter applicationConfigAdapter;
    private final MoexSyncDao moexSyncDao;
    private final HolidayService holidayService;

    public void sync() {
        if (getSyncDate() != null && !getSyncDate().isBefore(LocalDate.now())) {
            return;
        }
        log.info("Startup sync securities candles START");
        Scheduler scheduler1 = Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync1");

        Flux.fromIterable(List.of(1, 2, 3))
                .publishOn(scheduler1)
                .flatMapSequential(listing ->
                        specificationsSecuritiesHandler.fetchByListingLvl(listing)
                                .flatMap(sec -> Mono.fromCallable(sec::sec_id)
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M1, sec.sec_id()))
                                                .subscribe())
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M10, sec.sec_id()))
                                                .subscribe())
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.M60, sec.sec_id()))
                                                .subscribe())
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D1, sec.sec_id()))
                                                .subscribe())
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D7, sec.sec_id()))
                                                .subscribe())
                                        .doOnNext(i -> securitiesCandlesHandler.fetch(createRequest(Interval.D31, sec.sec_id()))
                                                .subscribe())
                                )
                )
                .log()
                .blockLast();

        moexSyncDao.save(MoexSync.builder()
                        .dataType(SyncTypes.candles.name())
                        .build())
                .log()
                .subscribe(i -> log.info("Startup sync securities candles FINISH"));
    }

    @SneakyThrows
    private Object syncProcess(SupplierMethodInvocation supplierMethodInvocation, Interval interval) {
        var lockName = String.format(CATEGORY.concat("-%s"), interval);
        return invokeWithLock(lockService, new SecuritiesCandlesSyncLock(lockName),
                applicationConfigAdapter.resourceLockTimeout(),
                supplierMethodInvocation);
    }

    private RequestParamSecuritiesCandles createRequest(Interval interval, String sec) {
        var now = LocalDate.now();
        var maxDate = now.minusDays(1L);

        var minActual = getMinDateActual(maxDate, interval, sec);

        return RequestParamSecuritiesCandles.builder()
                .security(sec)
                .board(null)
                .interval(interval.value)
                .reverse(false)
                .build()
                .till(maxDate)
                .from(minActual);
    }

    @SneakyThrows
    private LocalDate getMinDateActual(LocalDate maxDate, Interval interval, String sec) {
        var intervalToMoexMap = Map.of(
                Interval.M1, 1,
                Interval.M10, 3,
                Interval.M60, 3,
                Interval.D1, 12 * 5,
                Interval.D7, 12 * 5,
                Interval.D31, 12 * 20);

        var now = LocalDate.now();
        var minDate = maxDate.minusMonths(intervalToMoexMap.get(interval));
        minDate = validateDayNotWeekend(minDate, maxDate, sec);
        var syncDate = Optional.ofNullable(getSyncDate()).orElse(minDate);
        syncDate = validateDayNotWeekend(syncDate, maxDate, sec);


        return Stream.of(syncDate, maxDate)
                .filter(it -> it.isBefore(now))
                .min(LocalDate::compareTo).get();
    }

    @SneakyThrows
    private LocalDate getSyncDate() {
        return Optional.ofNullable(moexSyncDao.findByDataTypeOrderByUpdateDateDesc(SyncTypes.candles).toFuture().get())
                .map(it -> it.getUpdateDate().toLocalDate())
                .orElse(null);
    }

    private LocalDate validateDayNotWeekend(LocalDate startDate, LocalDate maxDate, String security) {
        while (holidayService.isHoliday(startDate, null, security)) {
            if (startDate.isBefore(maxDate))
                startDate = startDate.plusDays(1L);
            else break;
        }
        return startDate;
    }

}
