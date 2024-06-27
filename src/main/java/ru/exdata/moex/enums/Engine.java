package ru.exdata.moex.enums;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;

@Serdeable
@AllArgsConstructor
public enum Engine {

    Stock("stock");

    public final String value;

    public String getValue() {
        return value;
    }

}
