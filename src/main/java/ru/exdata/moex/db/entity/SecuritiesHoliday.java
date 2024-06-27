package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDate;

@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "securities_holiday", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class SecuritiesHoliday {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;
    private String boardId;                  /*"TQBR"*/
    private LocalDate tradeDate;             /*"2024-06-26"*/
    private String secId;                    /*"SBER"*/

}
