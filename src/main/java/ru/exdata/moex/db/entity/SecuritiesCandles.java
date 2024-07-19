package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "securities_candles", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class SecuritiesCandles {

    @EmbeddedId
    private SecuritiesCandlesPk securitiesCandlesPk;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double value;
    private Double volume;

}
