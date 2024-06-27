package ru.exdata.moex.mapper;

import ru.exdata.moex.db.entity.SecuritiesHistory;
import ru.exdata.moex.dto.history.SecuritiesHistoryDto;

import java.util.List;

public class SecuritiesHistoryMapper {

    public static List<SecuritiesHistory> fromDtoListToEntityList(List<SecuritiesHistoryDto> dtos) {
        return dtos.stream()
                .flatMap(it -> it.getSecuritiesHistory().getData().stream())
                .map(SecuritiesHistoryMapper::fromArrToEntity)
                .toList();
    }

    public static Object[] fromEntityToArrSecuritiesTrades(SecuritiesHistory sec) {
        Object[] it = new Object[23];
        it[0] = sec.getBoardId();
        it[1] = sec.getTradeDate();
        it[2] = sec.getShortName();
        it[3] = sec.getSecId();
        it[4] = sec.getNumTrades();
        it[5] = sec.getValue();
        it[6] = sec.getOpen();
        it[7] = sec.getLow();
        it[8] = sec.getHigh();
        it[9] = sec.getLegalClosePrice();
        it[10] = sec.getWaPrice();
        it[11] = sec.getClose();
        it[12] = sec.getVolume();
        it[13] = sec.getMarketPrice2();
        it[14] = sec.getMarketPrice3();
        it[15] = sec.getAdminTedQuote();
        it[16] = sec.getMp2valtrd();
        it[17] = sec.getMarketPrice3TradesValue();
        it[18] = sec.getAdmittedValue();
        it[19] = sec.getWaval();
        it[20] = sec.getTradingSession();
        it[21] = sec.getCurrencyId();
        it[22] = sec.getTrendclspr();
        return it;
    }

    public static SecuritiesHistory fromArrToEntity(Object[] it) {
        return new SecuritiesHistory(
                null,
                (String) it[0],                              /*"TQBR"*/
                MapperUtils.mapFromObjectToLocalDate(it[1]), /*"2024-06-26"*/
                (String) it[2],                              /*"Сбербанк"*/
                (String) it[3],                              /*"SBER"*/
                MapperUtils.mapFromObjectToInt(it[4]),       /*88589*/
                MapperUtils.mapFromObjectToDouble(it[5]),    /*13721465556.5*/
                MapperUtils.mapFromObjectToDouble(it[6]),    /*320.1*/
                MapperUtils.mapFromObjectToDouble(it[7]),    /*319.8*/
                MapperUtils.mapFromObjectToDouble(it[8]),    /*324.56*/
                MapperUtils.mapFromObjectToDouble(it[9]),    /*323.9*/
                MapperUtils.mapFromObjectToDouble(it[10]),   /*322.94*/
                MapperUtils.mapFromObjectToDouble(it[11]),   /*324.55*/
                MapperUtils.mapFromObjectToDouble(it[12]),   /*42491110*/
                MapperUtils.mapFromObjectToDouble(it[13]),   /*322.82*/
                MapperUtils.mapFromObjectToDouble(it[14]),   /*322.82*/
                MapperUtils.mapFromObjectToDouble(it[15]),   /*null*/
                MapperUtils.mapFromObjectToDouble(it[16]),   /*12655585662.7*/
                MapperUtils.mapFromObjectToDouble(it[17]),   /*12655585662.7*/
                MapperUtils.mapFromObjectToDouble(it[18]),   /*null*/
                MapperUtils.mapFromObjectToDouble(it[19]),   /*0*/
                MapperUtils.mapFromObjectToInt(it[20]),      /*3*/
                (String) it[21],                             /*"SUR"*/
                MapperUtils.mapFromObjectToDouble(it[22])    /*1.49*/
        );
    }

}
