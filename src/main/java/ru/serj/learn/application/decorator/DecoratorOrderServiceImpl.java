package ru.serj.learn.application.decorator;

import ru.serj.learn.application.service.TimeSpanManager;
import ru.serj.learn.application.service.TransactionManager;
import ru.serj.learn.core.api.CreateOrderRequest;
import ru.serj.learn.core.service.OrderService;

public class DecoratorOrderServiceImpl implements OrderService {

    private final TransactionManager transactionManager;
    private final TimeSpanManager timeSpanManager;
    private final OrderService coreService;

    public DecoratorOrderServiceImpl(TransactionManager transactionManager,
                                     TimeSpanManager timeSpanManager,
                                     OrderService coreService) {
        this.transactionManager = transactionManager;
        this.timeSpanManager = timeSpanManager;
        this.coreService = coreService;
    }

    @Override
    public void create(CreateOrderRequest createOrderRequest) {
        try {
            timeSpanManager.startTimeSpan("Создание заказа");
            transactionManager.begin();

            coreService.create(createOrderRequest);

            transactionManager.commit();
        } finally {
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
}
