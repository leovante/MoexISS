package ru.exdata.moex.handler.event;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class Startup {

    private final ApplicationContext ctx;

    @EventListener
    public void onStartup(StartupEvent event) {
        ctx.getBean(SecuritiesSync.class).sync();
        ctx.getBean(SecuritiesCandlesSync.class).sync();
    }

}
