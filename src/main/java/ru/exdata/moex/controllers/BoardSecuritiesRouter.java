package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.handler.BoardSecuritiesHandler;
import ru.exdata.moex.handler.SecuritiesCandlesHandler;

@Validated
@RequiredArgsConstructor
@Controller("/iss")
public class BoardSecuritiesRouter {

    private final BoardSecuritiesHandler securitiesService;
    @Named("SecuritiesCandlesHandler")
    @Inject
    private SecuritiesCandlesHandler securitiesCandlesHandler;

    @Get("/engines/stock/markets/shares/boards/{board}/securities/columns")
    @Status(HttpStatus.OK)
    public Mono<Object> fetchColumns(@PathVariable String board) {
        return securitiesService.fetchColumns();
    }

    @Get("/engines/stock/markets/shares/boards/{board}/securities")
    @Status(HttpStatus.OK)
    public Mono<Object> fetchSecurities(@PathVariable String board) {
        return securitiesService.fetchSecurities();
    }

    @Get("/engines/stock/markets/shares/boards/{board}/securities/{security}/candles")
    @Status(HttpStatus.OK)
    public Flux<Row> fetchSecurities(@Valid @RequestBean RequestParamSecuritiesCandles request) {
        return securitiesCandlesHandler.fetch(request);
    }

}
