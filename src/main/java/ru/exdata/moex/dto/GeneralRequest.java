package ru.exdata.moex.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

import static java.util.concurrent.TimeUnit.DAYS;

public class GeneralRequest {

    public Duration getDuration() {
        return Duration.ofDays(DAYS.toChronoUnit().between(getFrom(), getTill()));
    }

    public LocalDate getFrom() {
        return null;
    }

    public LocalDate getTill() {
        return null;
    }

}
