package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "resource_lock", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class ResourceLockEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Ресурс
     */
    private String resourceName;

    /**
     * Дата создания
     */
    @DateCreated
    private LocalDateTime dateCreate;

    /**
     * Дата действия
     */
    private LocalDateTime dateExpire;

}
