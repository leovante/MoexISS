package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.event.PrePersist;
import io.micronaut.data.model.naming.NamingStrategies;
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
@MappedEntity(value = "securities", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class Securities {

    @Id
    private Integer id;
    private String secId;
    @DateCreated
    private LocalDateTime createDate;
    @DateUpdated
    private LocalDateTime updateDate;

}
