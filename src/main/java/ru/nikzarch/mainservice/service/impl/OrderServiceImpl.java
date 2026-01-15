package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.domain.order.OrderStatus;
import ru.nikzarch.mainservice.domain.order.dto.OrderDTO;
import ru.nikzarch.mainservice.repository.LocationRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.service.NotificationService;
import ru.nikzarch.mainservice.service.OrderService;
import ru.nikzarch.monsterservice.service.impl.MonsterServiceImpl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final MonsterServiceImpl monsterService;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO order) {
        return orderRepository.save(Order.builder()
                .name(order.name())
                .monster(monsterService.getById(order.monsterId()))
                .description(order.description())
                .location(locationRepository.getReferenceById(order.locationId()))
                .reward(order.reward())
                .createdAt(ZonedDateTime.now())
                .orderStatus(order.orderStatus())
                .userId(order.userId())
                .build()
        );
    }

    @Override
    public List<Order> getAvailableOrders() {
        return orderRepository.findAll()
                .stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Order acceptOrder(Long orderId, Long witcherId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Order already accepted");
        }

        order.setOrderStatus(OrderStatus.ACTIVE);
        order.setUserId(witcherId);

        notificationService.notifyPeasantOrderAccepted(order);

        return order;
    }

    @Override
    public Order completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }

    @Override
    public Order markExpired(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(OrderStatus.CLOSED);
        return orderRepository.save(order);
    }
}
