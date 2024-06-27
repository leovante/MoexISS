package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.dto.RequestParamSecuritiesHistory;
import ru.exdata.moex.handler.SecuritiesHistoryHandler;

@Validated
@RequiredArgsConstructor
@Controller("/iss/history/engines/stock/markets/shares")
public class SecuritiesHistoryRouter {

    private final SecuritiesHistoryHandler securitiesHistoryHandler;

    @Get("/boards/{board}/securities/{security}")
    @Status(HttpStatus.OK)
    public Flux<Object[]> getSecuritiesHistory(@Valid @RequestBean RequestParamSecuritiesHistory request) {
        return securitiesHistoryHandler.fetch(request);
    }

}
