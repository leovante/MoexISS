package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;

@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SecuritiesCandlesPk {

    @MappedProperty("sec_id")
    private String secId;
    @MappedProperty(value = "begin_at", type = DataType.TIMESTAMP)
    private LocalDateTime beginAt;
    @MappedProperty(value = "end_at", type = DataType.TIMESTAMP)
    private LocalDateTime endAt;

}
