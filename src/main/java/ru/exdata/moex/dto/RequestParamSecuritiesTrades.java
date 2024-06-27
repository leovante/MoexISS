package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Serdeable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestParamSecuritiesTrades {

    @QueryValue(defaultValue = "stock")
    private String engine;
    @QueryValue(defaultValue = "shares")
    private String market;
    @PathVariable
    private String security;

    @QueryValue(defaultValue = "0")
    private long tradeno;
    @QueryValue(defaultValue = "0")
    private int start;
//    @Nullable
//    @QueryValue(defaultValue = "5000")
//    private int limit;

    public String getEngine() {
        return engine.toUpperCase();
    }

    public String getMarket() {
        return market.toUpperCase();
    }

    public String getSecurity() {
        return security.toUpperCase();
    }

}
