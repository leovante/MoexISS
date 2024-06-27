package ru.exdata.moex.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
@Data
public class SecuritiesDto {

    private Securities securities;

}
