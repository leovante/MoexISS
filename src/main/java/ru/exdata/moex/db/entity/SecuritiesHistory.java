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
@MappedEntity(value = "securities_history", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase.class)
public class SecuritiesHistory {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Long id;
    private String boardId;                  /*"TQBR"*/
    private LocalDate tradeDate;             /*"2024-06-26"*/
    private String shortName;                /*"Сбербанк"*/
    private String secId;                    /*"SBER"*/
    private Integer numTrades;               /*88589*/
    private Double value;                    /*13721465556.5*/
    private Double open;                     /*320.1*/
    private Double low;                      /*319.8*/
    private Double high;                     /*324.56*/
    private Double legalClosePrice;          /*323.9*/
    private Double waPrice;                  /*322.94*/
    private Double close;                    /*324.55*/
    private Double volume;                   /*42491110*/
    private Double marketPrice2;             /*322.82*/
    private Double marketPrice3;             /*322.82*/
    private Double adminTedQuote;            /*null*/
    private Double mp2valtrd;                /*12655585662.7*/
    private Double marketPrice3TradesValue;  /*12655585662.7*/
    private Double admittedValue;            /*null*/
    private Double waval;                    /*0*/
    private Integer tradingSession;          /*3*/
    private String currencyId;               /*"SUR"*/
    private Double trendclspr;               /*1.49*/

}
