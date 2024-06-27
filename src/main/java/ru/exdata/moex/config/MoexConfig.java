package ru.exdata.moex.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;

@ConfigurationProperties(MoexConfig.PREFIX)
@Requires(property = MoexConfig.PREFIX)
public record MoexConfig(
        String organization,
        String repo,
        @Nullable String username,
        @Nullable String token
) {

    public static final String PREFIX = "moex";

}
