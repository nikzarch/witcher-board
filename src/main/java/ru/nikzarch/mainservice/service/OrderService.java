package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.domain.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO order);

    List<Order> getAvailableOrders();

    Order acceptOrder(Long orderId, Long witcherId);

    Order completeOrder(Long orderId);

    Order markExpired(Long orderId);
}
