package ru.exdata.moex.enums;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@Introspected
@AllArgsConstructor
public enum Interval {

    M1(1),
    M10(10),
    M60(60),
    D1(24),
    D7(7),
    D31(31),
    ;

    public static Interval valueOf(Integer text) {
        for (Interval b : Interval.values()) {
            if (Objects.equals(b.value, text)) {
                return b;
            }
        }
        return null;
    }

    public final Integer value;

}
