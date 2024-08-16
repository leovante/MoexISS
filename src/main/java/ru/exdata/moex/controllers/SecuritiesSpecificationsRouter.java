package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Status;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.db.records.rSecuritiesSpec;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;

@RequiredArgsConstructor
@Controller("/specifications")
public class SecuritiesSpecificationsRouter {

    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;

    @Get("/securities/listing/{listing}")
    @Status(HttpStatus.OK)
    public Flux<rSecuritiesSpec> getSecurities(@PathVariable Integer listing) {
        return specificationsSecuritiesHandler.fetchByListingLvl(listing);
    }

}
