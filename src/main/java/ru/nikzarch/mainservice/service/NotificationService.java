package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.order.Order;

public interface NotificationService {

    void notifyWitcherNewOrder(Order order);

    void notifyPeasantOrderAccepted(Order order);
}
