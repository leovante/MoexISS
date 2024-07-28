package ru.exdata.moex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Security {

    Sber("sber");

    public final String value;

}
