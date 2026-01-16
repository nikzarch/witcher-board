package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.mainservice.domain.battles.Battles;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.repository.BattlesRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.service.BattleService;
import ru.nikzarch.witcherboard.mongo.document.InventoryDocument;
import ru.nikzarch.witcherboard.mongo.document.ItemDocument;
import ru.nikzarch.witcherboard.mongo.repository.InventoryRepository;
import ru.nikzarch.witcherboard.mongo.repository.ItemRepository;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final OrderRepository orderRepository;
    private final BattlesRepository battlesRepository;
    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public boolean fight(Long orderId, Long witcherId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var monster = order.getMonster();

        int chance = 50;

        Set<String> monsterFeatures = monster.getMonsterFeature()
                .stream()
                .map(f -> f.getId().toString())
                .collect(Collectors.toSet());

        InventoryDocument inventory = inventoryRepository.findByWitcherId(witcherId);

        if (inventory != null) {
            List<ItemDocument> items = itemRepository.findAllById(inventory.getItemIds());

            for (ItemDocument item : items) {
                for (String featureId : item.getMonsterBonuses().keySet()) {
                    if (monsterFeatures.contains(featureId)) {
                        chance += 5;
                    }
                }
            }
        }

        chance = Math.min(chance, 95);
        chance = Math.max(chance, 5);

        int roll = new Random().nextInt(100) + 1;
        boolean win = roll <= chance;
        
        Battles battle = new Battles();
        battle.setWitcherId(witcherId);
        battle.setMonsterId(monster);
        battle.setBattleSuccess(win);

        battlesRepository.save(battle);

        return win;
    }
}
