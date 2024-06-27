package ru.exdata.moex.config;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.liquibase.LiquibaseConfigurationProperties;
import io.micronaut.liquibase.LiquibaseMigrator;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Singleton
public class LiquibaseConfig {

    private final LiquibaseMigrator liquibaseMigrator;
    private final LiquibaseConfigurationProperties liquibaseConfigurationProperties;
    @Inject
    @Named("liquibase")
    private DataSource dataSource;

    /*@EventListener
    public void runLiquibase(StartupEvent startupEvent) {
        liquibaseMigrator.run(liquibaseConfigurationProperties, dataSource);
    }*/

}
