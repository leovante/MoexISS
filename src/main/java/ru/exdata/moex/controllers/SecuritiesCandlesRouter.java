package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.handler.SecuritiesCandlesHandler;

@Validated
@RequiredArgsConstructor
@Controller("/iss")
public class SecuritiesCandlesRouter {

    @Named("SecuritiesCandlesHandler")
    @Inject
    private SecuritiesCandlesHandler securitiesCandlesHandler;

    @Get("/engines/stock/markets/shares/securities/{security}/candles")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Flux<Row> getSecuritiesTrades(@Valid @RequestBean RequestParamSecuritiesCandles request) {
        return securitiesCandlesHandler.fetch(request);
    }

}
