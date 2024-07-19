package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.MappedProperty;
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
    @MappedProperty("begin_at")
    private LocalDateTime beginAt;
    @MappedProperty("end_at")
    private LocalDateTime endAt;

}
