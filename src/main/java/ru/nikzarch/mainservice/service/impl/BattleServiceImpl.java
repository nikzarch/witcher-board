package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.mainservice.domain.battles.Battles;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.repository.BattlesRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.service.BattleService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final OrderRepository orderRepository;
    private final BattlesRepository battlesRepository;

    @Override
    public boolean fight(Long orderId, Long witcherId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        int monsterPower = order.getMonster().getDangerLevel();
        int witcherPower = new Random().nextInt(10) + 5;

        boolean win = witcherPower >= monsterPower;

        Battles battle = new Battles();
        battle.setWitcherId(witcherId);
        battle.setMonsterId(order.getMonster());
        battle.setBattleSuccess(win);

        battlesRepository.save(battle);

        return win;
    }
}
