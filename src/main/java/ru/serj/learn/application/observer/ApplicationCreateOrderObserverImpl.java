package ru.serj.learn.application.observer;

import ru.serj.learn.application.service.TimeSpanManager;
import ru.serj.learn.application.service.TransactionManager;
import ru.serj.learn.core.service.CreateOrderObserver;

public class ApplicationCreateOrderObserverImpl implements CreateOrderObserver {

    private final TransactionManager transactionManager;
    private final TimeSpanManager timeSpanManager;

    public ApplicationCreateOrderObserverImpl(TransactionManager transactionManager,
                                              TimeSpanManager timeSpanManager) {
        this.transactionManager = transactionManager;
        this.timeSpanManager = timeSpanManager;
    }

    @Override
    public void onStart() {
        timeSpanManager.startTimeSpan("Создание заказа");
        transactionManager.begin();
    }

    @Override
    public void onEnd() {
        transactionManager.commit();
    }

    @Override
    public void onFinally() {
        try {
            if (transactionManager.isActive()) {
                transactionManager.rollback();
            }
        } finally {
            if (timeSpanManager.isActive()) {
                timeSpanManager.stopTimeSpan();
            }
        }
    }
}
