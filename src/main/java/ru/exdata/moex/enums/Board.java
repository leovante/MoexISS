package ru.exdata.moex.enums;

public enum Board {
    TQBR("TQBR");

    Board(String value) {
        this.value = value;
    }
    public final String value;
    public String getValue() {
        return value;
    }
}
