package ru.exdata.moex.enums;

public enum Security {
    Sber("sber");

    Security(String value) {
        this.value = value;
    }
    public final String value;
    public String getValue() {
        return value;
    }
}
