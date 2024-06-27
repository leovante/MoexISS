package ru.exdata.moex.db.entity;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.model.naming.NamingStrategies;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Serdeable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "securities_trades", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class SecuritiesTrades {

    @Id
    private Long tradeNo;
    private LocalTime tradeTime;        /*         "09:59:33",+*/
    private String boardIf;             /*            "TQBR",*/
    private String secId;               /*          "AFLT",+*/
    private Double price;               /*          58.79,+*/
    private Double quantity;            /*      8,+*/
    private Double value;               /*  4703.2,+*/
    private String period;              /*   "S",+*/
    private Long tradeTimeGrp;          /*           959,*/
    private LocalDateTime sysTime;      /*     "2024-07-05 09:59:34",+*/
    private String buySell;             /*    "B",+*/
    // "DECIMALS"                2;
    private String tradingSession;      /*          "1"+*/

}
