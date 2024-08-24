package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Introspected(excludes = "duration")
@Serdeable
@Data
@NoArgsConstructor
public class RequestParamSecuritiesCandles extends GeneralRequest {

    private final static String FORMAT_DATE = "yyyy-MM-dd";

    public RequestParamSecuritiesCandles(String security,
                                         String board,
                                         LocalDate from,
                                         LocalDate till,
                                         Integer interval,
                                         Boolean reverse) {
        super();
        this.security = security;
        this.board = board;
        this.from = from;
        this.till = till;
        this.interval = interval;
        this.reverse = reverse;
    }

    @PathVariable
    private String security;
    @PathVariable
    @Nullable
    private String board;
    @Format(FORMAT_DATE)
    @QueryValue
    private LocalDate from;
    @Format(FORMAT_DATE)
    @QueryValue
    @Nullable
    private LocalDate till;
    @QueryValue
    private Integer interval;
    @QueryValue(defaultValue = "false")
    private Boolean reverse;

    public String getSecurity() {
        return security.toUpperCase();
    }

}
