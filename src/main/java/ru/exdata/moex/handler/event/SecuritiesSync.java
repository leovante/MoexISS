package ru.exdata.moex.handler.event;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.exdata.moex.db.dao.MoexSyncDao;
import ru.exdata.moex.db.entity.MoexSync;
import ru.exdata.moex.dto.RequestParamSecurities;
import ru.exdata.moex.dto.securities.Row;
import ru.exdata.moex.handler.SecuritiesHandler;
import ru.exdata.moex.handler.SecuritiesSpecHandler;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesSync {

    private final MoexSyncDao moexSyncDao;
    private final SecuritiesHandler securitiesService;
    private final SecuritiesSpecHandler securitiesSpecHandler;

    public void sync() {
        log.info("Startup sync securities START");
        Schedulers.newBoundedElastic(1, Integer.MAX_VALUE, "schedulerSecuritiesCandlesSync1")
                .schedule(() -> moexSyncDao.findByDataTypeOrderByUpdateDateDesc(SyncTypes.securities)
                        .filter(it -> it.getUpdateDate().toLocalDate().isBefore(LocalDate.now()))
                        .flux()
                        .flatMap(sync -> fetchStocks()
                                .map(it -> fetchSpecs(it.getSecId())))
                        .blockLast());

        moexSyncDao.save(MoexSync.builder()
                        .dataType(SyncTypes.securities.name())
                        .build())
                .log()
                .subscribe(i -> log.info("Startup sync securities FINISH"));
    }

    private Flux<Row> fetchStocks() {
        return securitiesService.fetch(RequestParamSecurities.builder()
                .isTrading("1")
                .engine("stock")
                .market("shares")
                .start(0)
                .lang("ru")
                .q(null)
                .limit(100)
                .issOnly("trade_engines")
                .build());
    }

    private Flux<ru.exdata.moex.dto.securitiesSpec.Row> fetchSpecs(String secId) {
        return securitiesSpecHandler.fetchBySecId(secId);
    }

}
