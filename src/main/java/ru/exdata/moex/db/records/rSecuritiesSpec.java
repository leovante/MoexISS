package ru.exdata.moex.db.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record rSecuritiesSpec(
        @JsonProperty("secId") String sec_id,
        @JsonProperty("isin") String isin
) {
}
