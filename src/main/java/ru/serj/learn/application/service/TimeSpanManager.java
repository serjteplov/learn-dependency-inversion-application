package ru.serj.learn.application.service;

public interface TimeSpanManager {
    void startTimeSpan(String name);

    boolean isActive();

    void stopTimeSpan();
}
