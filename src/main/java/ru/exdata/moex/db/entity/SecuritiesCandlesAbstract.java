package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.EmbeddedId;
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
public abstract class SecuritiesCandlesAbstract {

    @EmbeddedId
    private SecuritiesCandlesPk securitiesCandlesPk;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double value;
    private Double volume;

}
