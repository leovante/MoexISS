package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(callSuper = false)
@Introspected(excludes = "duration")
@Builder
@Serdeable
@Data
@NoArgsConstructor
public class RequestParamSecuritiesCandles extends GeneralRequest {

    private final static String FORMAT_DATE = "yyyy-MM-dd";

    public RequestParamSecuritiesCandles(String security,
                                         String board,
                                         LocalDate fromDate,
                                         LocalDate tillDate,
                                         Integer interval,
                                         Boolean reverse) {
        super();
        this.security = security;
        this.board = board;
        this.from.set(fromDate);
        this.till.set(tillDate);
        this.interval = interval;
        this.reverse = reverse;
    }

    public RequestParamSecuritiesCandles(String security,
                                         String board,
                                         Integer interval,
                                         Boolean reverse) {
        super();
        this.security = security;
        this.board = board;
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
    private LocalDate fromDate;

    @Schema(hidden = true)
    private final AtomicReference<LocalDate> from = new AtomicReference<LocalDate>();

    @Format(FORMAT_DATE)
    @QueryValue
    @Nullable
    private LocalDate tillDate;

    @Schema(hidden = true)
    private final AtomicReference<LocalDate> till = new AtomicReference<LocalDate>();

    @QueryValue
    private Integer interval;

    @QueryValue(defaultValue = "false")
    private Boolean reverse;

    public String getSecurity() {
        return security.toUpperCase();
    }

    public void setFrom(LocalDate date) {
        from.set(date);
    }

    public RequestParamSecuritiesCandles from(LocalDate date) {
        from.set(date);
        return this;
    }

    public LocalDate getFrom() {
        return from.get();
    }

    public void setTill(LocalDate date) {
        till.set(date);
    }

    public RequestParamSecuritiesCandles till(LocalDate date) {
        till.set(date);
        return this;
    }

    public LocalDate getTill() {
        return till.get();
    }


}
