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
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Mono<Object> fetchColumnsStream(@PathVariable String board) {
        return securitiesService.fetchColumns();
    }

    @Get("/engines/stock/markets/shares/boards/{board}/securities")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Mono<Object> fetchSecuritiesStream(@PathVariable String board) {
        return securitiesService.fetchSecurities();
    }

    @Get("/engines/stock/markets/shares/boards/{board}/securities/{security}/candles")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Flux<Row> fetchSecuritiesCandlesStream(@Valid @RequestBean RequestParamSecuritiesCandles request) {
        return securitiesCandlesHandler.fetch(request);
    }

}
