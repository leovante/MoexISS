package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;

@Serdeable
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "moex_sync", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class MoexSync {

    @Id
    @GeneratedValue
    private Long id;
    private String dataType;

    @DateUpdated
    private LocalDateTime updateDate;

}
