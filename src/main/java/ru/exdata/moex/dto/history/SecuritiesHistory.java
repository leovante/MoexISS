package ru.exdata.moex.dto.history;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * "TQBR",
 * "2024-06-25",
 * "Сбербанк",
 * "SBER",
 * 75103,
 * 8270536250.9,
 * 317.5,
 * 316.28,
 * 319.89,
 * 319.21,
 * 318.35,
 * 319.8,
 * 25978180,
 * 318.19,
 * 318.19,
 * null,
 * 7265311020.7,
 * 7265311020.7,
 * null,
 * 0,
 * 3,
 * "SUR",
 * 0.8
 */
@Serdeable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecuritiesHistory {

    //    private Object metadata;
    private String[] columns;
    private List<Object[]> data;
    //    private Object dataversion;

}
