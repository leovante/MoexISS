package ru.exdata.moex.handler.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Document;

import java.time.LocalDate;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "moex")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = MediaType.APPLICATION_XML)
public interface SecuritiesCandlesApiClient {

    @Get("/iss/engines/stock/markets/shares/securities/{security}/candles?iss.reverse={reverse}")
    @SingleResult
    Mono<Document> fetch(@QueryValue String security,
                         @QueryValue String from,
                         @QueryValue String till,
                         @QueryValue(defaultValue = "0") String start,
                         @QueryValue Integer interval,
                         Boolean reverse
    );

    default Mono<Document> fetch(RequestParamSecuritiesCandles request, LocalDate from, LocalDate till, Integer pageNumber) {
        return fetch(
                request.getSecurity(),
                from.toString(),
                till.toString(),
                pageNumber.toString(),
                request.getInterval(),
                request.getReverse());
    }

}
