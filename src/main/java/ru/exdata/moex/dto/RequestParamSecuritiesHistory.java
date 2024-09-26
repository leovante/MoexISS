package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
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
                                         LocalDate from,
                                         LocalDate till) {
        super();
        this.security = security;
        this.board = board;
        this.from.set(from);
        this.till.set(till);
    }

    @PathVariable
    private String security;

    @PathVariable
    private String board;

    @Format(FORMAT_DATE)
    @QueryValue
    private LocalDate fromDate;

    @Schema(hidden = true)
    private AtomicReference<LocalDate> from = new AtomicReference<LocalDate>();

    @Format(FORMAT_DATE)
    @QueryValue
    private LocalDate tillDate;

    @Schema(hidden = true)
    private AtomicReference<LocalDate> till = new AtomicReference<LocalDate>();

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

    public AtomicReference<LocalDate> getAtomicFrom() {
        return from;
    }

    public void setTill(LocalDate date) {
        till.set(date);
    }

    public LocalDate getTill() {
        return till.get();
    }

}
