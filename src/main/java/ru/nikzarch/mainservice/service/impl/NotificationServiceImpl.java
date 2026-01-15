package ru.nikzarch.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyWitcherNewOrder(Order order) {
        // TODO: websocket / email / push
        System.out.println("New order in your region: " + order.getId());
    }

    @Override
    public void notifyPeasantOrderAccepted(Order order) {
        System.out.println("Your order accepted: " + order.getId());
    }
}
