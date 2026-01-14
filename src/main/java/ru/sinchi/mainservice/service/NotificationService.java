package ru.sinchi.mainservice.service;

import ru.sinchi.mainservice.domain.order.Order;

public interface NotificationService {

    void notifyWitcherNewOrder(Order order);

    void notifyPeasantOrderAccepted(Order order);
}
