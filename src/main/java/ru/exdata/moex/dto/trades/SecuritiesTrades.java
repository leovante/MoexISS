package ru.exdata.moex.dto.trades;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 10861767421,
 * 09:59:33,
 * TQBR,
 * AFLT,
 * 58.79,
 * 8,
 * 4703.2,
 * S,
 * 959,
 * 2024-07-05 09:59:34,
 * B,
 * 2,
 * 1
 */
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
@Data
public class SecuritiesTrades {

    //    private Object metadata;
    private String[] columns;
    private List<Object[]> data;
    private DataVersion dataversion;

//    private Long tradeNo;
//    private LocalTime tradeTime;
//    private String boardIf;
//    private String secId;
//    private Double price;
//    private Integer quantity;
//    private Double value;
//    private String period;
//    private Integer tradeTimeGrp;
//    private Instant sysTime;
//    private String buySell;
//    private Integer decimals;
//    private String tradingSession;

    @Serdeable
    @Data
    public static class DataVersion {
        private List<Integer> data;
    }

}
