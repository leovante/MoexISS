package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestBean;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.handler.SecuritiesCandlesHandler;

import java.util.List;

@Validated
@RequiredArgsConstructor
@Controller("/iss")
public class SecuritiesCandlesController {

    @Named("SecuritiesCandlesHandler")
    @Inject
    private SecuritiesCandlesHandler securitiesCandlesHandler;

    @SneakyThrows
    @Get("/engines/stock/markets/shares/securities/{security}/candles/sync")
    @Status(HttpStatus.OK)
    public List<Row> getSecuritiesCandlesSync(@Valid @RequestBean RequestParamSecuritiesCandles request) {
        return securitiesCandlesHandler.fetch(request).collectList().toFuture().get();
    }

}
