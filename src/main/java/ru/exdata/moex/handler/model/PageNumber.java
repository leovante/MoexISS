package ru.exdata.moex.handler.model;

public class PageNumber {

    private int page = 0;

    public void increment(int i) {
        page = page + i;
    }

    public int get() {
        return page;
    }

    public void stop() {
        page = -1;
    }

}
