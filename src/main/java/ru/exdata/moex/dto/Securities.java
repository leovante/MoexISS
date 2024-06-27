package ru.exdata.moex.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

import java.util.List;

/**
 * 424433251,
 * "ABIO",
 * "iАРТГЕН ао",
 * "1-01-08902-A",
 * "ПАО \"Артген\"",
 * "RU000A0JNAB6",
 * 1,
 * 1142,
 * "Публичное акционерное общество \"Артген биотех\"",
 * "7702508905",
 * "71328785",
 * "1-01-08902-A",
 * "common_share",
 * "stock_shares",
 * "TQBR",
 * "TQBR"
 */
@Serdeable
@Data
public class Securities {

//    private Object metadata;
//    private String[] columns;
    private List<Object[]> data;

}
