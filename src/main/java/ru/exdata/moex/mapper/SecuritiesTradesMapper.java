package ru.exdata.moex.mapper;

import ru.exdata.moex.db.entity.SecuritiesTrades;
import ru.exdata.moex.dto.trades.SecuritiesTradesDto;

import java.time.LocalTime;
import java.util.List;

public class SecuritiesTradesMapper {

    public static List<SecuritiesTrades> fromDtoListToEntityList(List<SecuritiesTradesDto> dtos) {
        return dtos.stream()
                .flatMap(it -> it.getSecuritiesTrades().getData().stream())
                .map(SecuritiesTradesMapper::fromArrToEntity)
                .toList();
    }

    public static Object[] fromEntityToArrSecuritiesTrades(SecuritiesTrades sec) {
        Object[] it = new Object[13];
        it[0] = sec.getTradeNo();
        it[1] = sec.getTradeTime();
        it[2] = sec.getBoardIf();
        it[3] = sec.getSecId();
        it[4] = sec.getPrice();
        it[5] = sec.getQuantity();
        it[6] = sec.getValue();
        it[7] = sec.getPeriod();
        it[8] = sec.getTradeTimeGrp();
        it[9] = sec.getSysTime();
        it[10] = sec.getBuySell();
//        it[11] = sec.getTradeNo();
        it[12] = sec.getTradingSession();
        return it;
    }

    public static SecuritiesTrades fromArrToEntity(Object[] it) {
        return new SecuritiesTrades(
                (long) it[0],
                LocalTime.parse((String) it[1]),
                (String) it[2],
                (String) it[3],
                MapperUtils.mapFromObjectToDouble(it[4]),
                MapperUtils.mapFromObjectToDouble(it[5]),
                MapperUtils.mapFromObjectToDouble(it[6]),
                (String) it[7],
                MapperUtils.mapFromObjectToLong(it[8]),
                MapperUtils.mapFromObjectToLocalDateTime(it[9]),
                (String) it[10],
                (String) it[12]
        );
    }

}
