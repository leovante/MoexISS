package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(callSuper = false)
@Introspected(excludes = "duration")
@Serdeable
@Data
@NoArgsConstructor
public class RequestParamSecuritiesHistory extends GeneralRequest {

    private final static String FORMAT_DATE = "yyyy-MM-dd";

    public RequestParamSecuritiesHistory(String security,
                                         String board,
                                         AtomicReference<LocalDate> from,
                                         AtomicReference<LocalDate> till) {
        super();
        this.security = security;
        this.board = board;
        this.from = from;
        this.till = till;
    }

    @PathVariable
    private String security;
    @PathVariable
    private String board;
    @Format(FORMAT_DATE)
    @QueryValue
    private AtomicReference<LocalDate> from;
    @Format(FORMAT_DATE)
    @QueryValue
    private AtomicReference<LocalDate> till;

    public String getSecurity() {
        return security.toUpperCase();
    }

    public String getBoard() {
        return board.toUpperCase();
    }

    public void setFrom(LocalDate date) {
        from.set(date);
    }

    public LocalDate getFrom() {
        return from.get();
    }

    public void setTill(LocalDate date) {
        till.set(date);
    }

    public LocalDate getTill() {
        return till.get();
    }

}
