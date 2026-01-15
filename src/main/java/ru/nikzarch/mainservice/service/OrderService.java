package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.order.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    List<Order> getAvailableOrders();

    Order acceptOrder(Long orderId, Long witcherId);

    Order completeOrder(Long orderId);

    Order markExpired(Long orderId);
}
