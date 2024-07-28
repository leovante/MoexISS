package ru.exdata.moex.mapper;

import ru.exdata.moex.db.entity.SecuritiesCandlesAbstract;
import ru.exdata.moex.db.entity.SecuritiesCandlesPk;
import ru.exdata.moex.dto.RequestParamSecuritiesCandles;
import ru.exdata.moex.dto.candles.Row;

public class SecuritiesCandlesMapper {

    public static Object[] fromDtoToArrSecuritiesCandles(Row sec) {
        Object[] it = new Object[8];
        it[0] = sec.getOpen();
        it[1] = sec.getClose();
        it[2] = sec.getHigh();
        it[3] = sec.getLow();
        it[4] = sec.getValue();
        it[5] = sec.getVolume();
        it[6] = sec.getBegin();
        it[7] = sec.getEnd();
        return it;
    }

    public static Row fromEntityToArrSecuritiesCandles(SecuritiesCandlesAbstract sec) {
        Row it = new Row();
        it.setOpen(sec.getOpen());
        it.setClose(sec.getClose());
        it.setHigh(sec.getHigh());
        it.setLow(sec.getLow());
        it.setValue(sec.getValue());
        it.setVolume(sec.getVolume());
        it.setBegin(sec.getSecuritiesCandlesPk().getBeginAt().toString());
        it.setEnd(sec.getSecuritiesCandlesPk().getEndAt().toString());
        return it;
    }

    public static <T extends SecuritiesCandlesAbstract> T fromArrToEntity(Row row,
                                                                          RequestParamSecuritiesCandles request,
                                                                          T securitiesCandles) {
        securitiesCandles.setSecuritiesCandlesPk(
                new SecuritiesCandlesPk(
                        request.getSecurity(),
                        MapperUtils.mapFromObjectToLocalDateTime(row.getBegin()),
                        MapperUtils.mapFromObjectToLocalDateTime(row.getEnd())));
        securitiesCandles.setOpen(row.getOpen());
        securitiesCandles.setClose(row.getClose());
        securitiesCandles.setHigh(row.getHigh());
        securitiesCandles.setLow(row.getLow());
        securitiesCandles.setValue(row.getValue());
        securitiesCandles.setVolume(row.getVolume());
        return securitiesCandles;
    }

}
