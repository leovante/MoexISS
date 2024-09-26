package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.exdata.moex.db.records.rSecuritiesSpec;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;

@RequiredArgsConstructor
@Controller("/specifications")
public class SecuritiesSpecificationsRouter {

    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;

    @Get("/securities/listing/{listing}")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    @Status(HttpStatus.OK)
    public Flux<rSecuritiesSpec> getSecuritiesStream(@PathVariable Integer listing) {
        return specificationsSecuritiesHandler.fetchByListingLvl(listing);
    }

}
