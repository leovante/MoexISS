package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Introspected(excludes = "duration")
@Serdeable
@Data
@NoArgsConstructor
public class RequestParamSecuritiesHistory extends GeneralRequest {

    private final static String FORMAT_DATE = "yyyy-MM-dd";

    public RequestParamSecuritiesHistory(String security, String board, LocalDate from, LocalDate till) {
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
    private LocalDate from;
    @Format(FORMAT_DATE)
    @QueryValue
    private LocalDate till;

    public String getSecurity() {
        return security.toUpperCase();
    }

    public String getBoard() {
        return board.toUpperCase();
    }
}
