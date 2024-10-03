package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.dto.RequestParamSecuritiesTrades;
import ru.exdata.moex.handler.SecuritiesTradesHandler;

@Validated
@RequiredArgsConstructor
@Controller("/iss")
public class SecuritiesTradesRouter {

    private final SecuritiesTradesHandler securitiesTradesService;

    @Get("/engines/stock/markets/shares/securities/{security}/trades")
    @Produces(value = "application/x-ndjson")
    @Status(HttpStatus.OK)
    public Flux<Object[]> getSecuritiesTradesStream(@Valid @RequestBean RequestParamSecuritiesTrades request) {
        return securitiesTradesService.fetch(request);
    }

}
