package ru.exdata.moex.config.properties;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import jakarta.validation.constraints.NotNull;

/**
 * Настройки приложения
 */
@ConfigurationProperties(ApplicationConfigAdapter.PREFIX)
@Requires(property = ApplicationConfigAdapter.PREFIX)
public record ApplicationConfigAdapter(
        @NotNull Long resourceLockTimeout
) {

    public static final String PREFIX = "app";

    /*@Singleton
    public class ApplicationConfigurationPropertiesImpl implements ApplicationConfigurationProperties {

        @Override
        public Long getResourceLockInterval() {
            return resourceLockTimeout;
        }

    }*/

}
