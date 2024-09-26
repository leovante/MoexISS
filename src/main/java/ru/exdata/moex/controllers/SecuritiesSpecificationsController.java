package ru.exdata.moex.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Status;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.exdata.moex.db.records.rSecuritiesSpec;
import ru.exdata.moex.handler.SpecificationsSecuritiesHandler;

import java.util.List;

@RequiredArgsConstructor
@Controller("/specifications")
public class SecuritiesSpecificationsController {

    private final SpecificationsSecuritiesHandler specificationsSecuritiesHandler;

    @SneakyThrows
    @Get("/securities/listing/{listing}/sync")
    @Status(HttpStatus.OK)
    public List<rSecuritiesSpec> getSecuritiesSpecificationsSync(@PathVariable Integer listing) {
        return specificationsSecuritiesHandler.fetchByListingLvl(listing).collectList().toFuture().get();
    }

}
