package ru.exdata.moex.handler;

public interface SmartLifecycle {

    int getPhase();

    void start();

    void stop();

    boolean isRunning();

}
