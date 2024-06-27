package ru.exdata.moex.handler.event;

public enum SyncTypes {
    Securities("securities");

    SyncTypes(String value) {
        this.value = value;
    }
    public final String value;
    public String getValue() {
        return value;
    }
}
