package ru.serj.learn.application.service;

public interface TransactionManager {
    void begin();

    void commit();

    void rollback();

    boolean isActive();
}
