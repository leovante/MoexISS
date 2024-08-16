package ru.exdata.moex.db.records;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record rSecuritiesSpec(
        String sec_id
) {
}
