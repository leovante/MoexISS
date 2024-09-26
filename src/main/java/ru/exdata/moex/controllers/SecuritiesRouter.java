package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.dto.RequestParamSecurities;
import ru.exdata.moex.dto.securities.Row;
import ru.exdata.moex.handler.SecuritiesHandler;

@Validated
@RequiredArgsConstructor
@Controller("/iss")
public class SecuritiesRouter {

    private final SecuritiesHandler securitiesService;

    @Get("/securities")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Flux<Row> getSecuritiesStream(@Valid @RequestBean RequestParamSecurities request) {
        return securitiesService.fetch(request);
    }

}
