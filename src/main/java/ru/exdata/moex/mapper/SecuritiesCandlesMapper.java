package ru.exdata.moex.mapper;

import ru.exdata.moex.db.entity.SecuritiesCandles;
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

    public static Row fromEntityToArrSecuritiesCandles(SecuritiesCandles sec) {
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

    public static SecuritiesCandles fromArrToEntity(Row row, RequestParamSecuritiesCandles request) {
        return new SecuritiesCandles(
                new SecuritiesCandlesPk(
                        request.getSecurity(),
                        MapperUtils.mapFromObjectToLocalDateTime(row.getBegin()),
                        MapperUtils.mapFromObjectToLocalDateTime(row.getEnd())),
                row.getOpen(),
                row.getClose(),
                row.getHigh(),
                row.getLow(),
                row.getValue(),
                row.getVolume()
        );
    }

}
