package ru.exdata.moex.handler.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;
import ru.exdata.moex.dto.history.SecuritiesHistoryDto;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "moex")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
public interface SecuritiesHistoryApiClient {

    @Get("/iss/history/engines/stock/markets/shares/boards/TQBR/securities/{security}.json")
    @SingleResult
    Mono<SecuritiesHistoryDto> fetch(@QueryValue String security,
                                     @QueryValue String from,
                                     @QueryValue String till,
                                     @QueryValue(defaultValue = "0") String start);

}
