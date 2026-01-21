package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.mainservice.domain.battles.BattleResult;
import ru.nikzarch.mainservice.domain.battles.dto.BattleResultDTO;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.domain.order.OrderStatus;
import ru.nikzarch.mainservice.mapper.BattleResultMapper;
import ru.nikzarch.mainservice.repository.BattleResultRepository;
import ru.nikzarch.mainservice.repository.OrderRepository;
import ru.nikzarch.mainservice.service.BattleService;
import ru.nikzarch.monsterservice.domain.MonsterFeature;
import ru.nikzarch.witcherboard.mongo.document.ItemDocument;
import ru.nikzarch.witcherboard.mongo.service.ItemService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BattleServiceImpl implements BattleService {

    private final OrderRepository orderRepository;
    private final BattleResultRepository battleResultRepository;
    private final ItemService itemService;

    private final BattleResultMapper battleResultMapper;

    private final Random random = new Random();

    @Override
    public BattleResultDTO fight(Long orderId, Long witcherId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.ACTIVE) {
            throw new IllegalStateException("Order is not active");
        }

        int monsterPower = order.getMonster().getDangerLevel();

        // 1) базовый шанс
        double baseChance = calculateBaseChance(monsterPower);

        // 2) бонусы от предметов ведьмака
        double bonus = calculateBonusFromItems(witcherId, order.getMonster().getMonsterFeature());

        double chance = baseChance + bonus;
        chance = Math.min(chance, 95.0); // ограничение сверху

        boolean win = random.nextDouble() * 100 <= chance;

        BattleResult entity = new BattleResult();
        entity.setOrderId(orderId);
        entity.setWitcherId(witcherId);
        entity.setMonsterId(order.getMonster().getId());
        entity.setSuccess(win);
        entity.setChance(chance);
        entity.setCreatedAt(LocalDateTime.now());

        battleResultRepository.save(entity);

        if (win) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }

        return new BattleResultDTO(
                orderId,
                witcherId,
                order.getMonster().getId(),
                win,
                chance,
                win ? "Победа! Монстр убит." : "Поражение. Вы ранены.",
                entity.getCreatedAt()
        );
    }

    @Override
    public List<BattleResultDTO> getHistory(Long witcherId) {
        return battleResultRepository.findByWitcherIdOrderByCreatedAtDesc(witcherId).stream().map(battleResultMapper::toDto).collect(Collectors.toList());
    }

    private double calculateBaseChance(int monsterPower) {
        double base = 70;
        double penalty = monsterPower * 2.5;
        double chance = base - penalty;
        if (chance < 5) chance = 5;
        if (chance > 95) chance = 95;
        return chance;
    }

    private double calculateBonusFromItems(Long witcherId, Set<MonsterFeature> monsterFeatures) {
        List<ItemDocument> items = itemService.getItemsByWitcherId(witcherId);

        Set<String> monsterFeatureIds = monsterFeatures.stream()
                .map(f -> String.valueOf(f.getId()))
                .collect(Collectors.toSet());

        int totalBonus = 0;
        for (ItemDocument item : items) {
            for (Map.Entry<String, Integer> bonusEntry : item.getMonsterBonuses().entrySet()) {
                String featureId = bonusEntry.getKey();
                Integer bonusValue = bonusEntry.getValue();

                if (monsterFeatureIds.contains(featureId)) {
                    totalBonus += bonusValue;
                }
            }
        }

        if (totalBonus > 25) totalBonus = 25;

        return totalBonus;
    }
}
