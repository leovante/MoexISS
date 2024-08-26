package ru.exdata.moex.handler.model;

import java.util.concurrent.atomic.AtomicInteger;

public class PageNumber {

    private final AtomicInteger page = new AtomicInteger(0);

    public void increment(int i) {
        page.addAndGet(i);
    }

    public int get() {
        return page.get();
    }

    public void stop() {
        page.set(-1);
    }

}
