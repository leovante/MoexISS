package ru.exdata.moex.controllers;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import ru.exdata.moex.handler.event.SecuritiesSyncEvent;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Controller("/sync")
public class SyncProcessorController {

    private final ApplicationEventPublisher<SecuritiesSyncEvent> eventPublisher;

    @Post("/securities")
    @Status(HttpStatus.CREATED)
    public HttpResponse<UUID> fetchSecuritiesSync() {
        var uuid = UUID.randomUUID();
        eventPublisher.publishEventAsync(SecuritiesSyncEvent.builder().processId(uuid).build());
        return HttpResponse.ok(uuid);
    }

}
