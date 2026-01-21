package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.domain.order.dto.OrderDTO;
import ru.nikzarch.mainservice.domain.order.dto.RewardDto;
import ru.nikzarch.mainservice.service.OrderService;
import ru.nikzarch.mainservice.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO order) {
        var newOrder =  orderService.createOrder(order);
        return ResponseEntity.ok("Размещён заказ с id" + newOrder.getId());
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAvailableOrders() {
        return ResponseEntity.ok(orderService.getAvailableOrders());
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<Order> acceptOrder(
            @PathVariable Long orderId,
            @RequestParam Long witcherId
    ) {
        return ResponseEntity.ok(orderService.acceptOrder(orderId, witcherId));
    }
}
