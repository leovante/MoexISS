package ru.exdata.moex.enums;

public enum Market {
    Shares("shares");

    Market(String value) {
        this.value = value;
    }
    public final String value;
    public String getValue() {
        return value;
    }
}
