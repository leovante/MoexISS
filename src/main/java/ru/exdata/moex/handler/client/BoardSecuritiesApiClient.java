package ru.exdata.moex.handler.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "moex")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
public interface BoardSecuritiesApiClient {

    @Get("/iss/engines/stock/markets/shares/boards/TQBR/securities/columns.json")
    @SingleResult
    Mono<Object> fetchColumns();

    @Get("/iss/engines/stock/markets/shares/boards/TQBR/securities.json")
    @SingleResult
    Mono<Object> fetchSecurities();

}
