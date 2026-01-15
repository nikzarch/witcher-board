package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.domain.order.OrderStatus;
import ru.nikzarch.mainservice.service.BattleService;
import ru.nikzarch.mainservice.service.OrderService;
import ru.nikzarch.mainservice.repository.OrderRepository;

@RestController
@RequestMapping("/api/v1/battles")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/")
    public ResponseEntity<BattleResult> fight(
            @RequestParam Long orderId,
            @RequestParam Long witcherId
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.ACTIVE) {
            throw new IllegalStateException("Order is not active");
        }

        boolean win = battleService.fight(orderId, witcherId);


        /*
         * Пока что так, потом сделаем шансы на выживание и тд, если сделаем хах
         */
        if (win) {
            orderService.completeOrder(orderId);
            return  ResponseEntity.ok(new BattleResult(true, "Победа! Монстр убит."));
        } else {
            return ResponseEntity.ok(new BattleResult(false, "Поражение. Вы ранены.")) ;
        }
    }


    public record BattleResult(
            boolean success,
            String message
    ) {}
}
