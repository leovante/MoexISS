package ru.exdata.moex.db.entity;

import io.micronaut.serde.annotation.Serdeable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public class SecuritiesCandles extends SecuritiesCandlesAbstract{

}
