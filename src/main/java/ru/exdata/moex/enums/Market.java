package ru.exdata.moex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Market {

    Shares("shares");

    public final String value;

}
