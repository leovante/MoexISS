package ru.exdata.moex.mapper;

import ru.exdata.moex.db.entity.Securities;
import ru.exdata.moex.dto.SecuritiesDto;

import java.util.List;

public class SecuritiesMapper {

    public static List<Securities> fromDtoListToEntityList(List<SecuritiesDto> dtos) {
        return dtos.stream()
                .flatMap(it -> it.getSecurities().getData().stream())
                .map(SecuritiesMapper::fromArrToEntity)
                .toList();
    }

    public static List<Securities> fromDtoToEntityList(SecuritiesDto dtos) {
        return dtos.getSecurities().getData().stream()
                .map(SecuritiesMapper::fromArrToEntity)
                .toList();
    }

    public static List<Securities> fromArrToEntity(List<Object[]> dtos) {
        return dtos.stream()
                .map(SecuritiesMapper::fromArrToEntity)
                .toList();
    }

    public static Securities fromArrToEntity(Object[] it) {
        var sec = new Securities();
        sec.setId(MapperUtils.mapFromObjectToInt(it[0]));
        sec.setSecId((String) it[1]);
        return sec;
    }

}
