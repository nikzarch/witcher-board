package ru.sinchi.monsterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sinchi.monsterservice.domain.Monster;
import ru.sinchi.monsterservice.repository.MonsterRepository;
import ru.sinchi.monsterservice.service.MonsterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;

    @Override
    public Monster createMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    @Override
    public Monster updateMonster(Long id, Monster monster) {
        Monster existing = getById(id);
        existing.setName(monster.getName());
        existing.setDangerLevel(monster.getDangerLevel());
        existing.setLocation(monster.getLocation());
        existing.setMonsterFeature(monster.getMonsterFeature());
        return monsterRepository.save(existing);
    }

    @Override
    public Monster getById(Long id) {
        return monsterRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Monster not found"));
    }

    @Override
    public List<Monster> getAll() {
        return monsterRepository.findAll();
    }

    /**
     * Автоматический расчёт награды
     * Формула:
     * base = dangerLevel * 100
     * + 50 за каждую слабость
     */
    @Override
    public int calculateRecommendedReward(Monster monster) {
        int base = monster.getDangerLevel() * 100;
        int featuresBonus = monster.getMonsterFeature() != null
                ? monster.getMonsterFeature().size() * 50
                : 0;
        return base + featuresBonus;
    }
}
