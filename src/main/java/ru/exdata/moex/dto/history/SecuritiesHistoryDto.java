package ru.exdata.moex.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Serdeable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecuritiesHistoryDto {

    @JsonProperty("history")
    private SecuritiesHistory securitiesHistory;

}
