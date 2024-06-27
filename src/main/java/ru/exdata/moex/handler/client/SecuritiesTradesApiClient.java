package ru.exdata.moex.handler.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;
import ru.exdata.moex.dto.trades.SecuritiesTradesDto;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "moex")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
public interface SecuritiesTradesApiClient {

    @Get("/iss/engines/stock/markets/shares/securities/{security}/trades.json")
    @SingleResult
    Mono<SecuritiesTradesDto> fetch(
            @PathVariable String security,
            @QueryValue(defaultValue = "stock") String engine,
            @QueryValue(defaultValue = "shares") String market,
            @QueryValue(defaultValue = "0") String tradeno,
            @QueryValue(defaultValue = "0") String start,
            @QueryValue(defaultValue = "100") String limit)
            ;

}
