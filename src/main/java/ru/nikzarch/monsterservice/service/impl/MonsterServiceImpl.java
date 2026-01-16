package ru.nikzarch.monsterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.monsterservice.domain.Monster;
import ru.nikzarch.monsterservice.repository.MonsterRepository;
import ru.nikzarch.monsterservice.service.MonsterService;

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
        if (id == null) throw new IllegalArgumentException("Monster id is null");
        return monsterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Monster not found"));
    }

    @Override
    public List<Monster> getAll() {
        return monsterRepository.findAll();
    }


    @Override
    public int calculateRecommendedReward(Monster monster) {
        int base = monster.getDangerLevel() * 100;
        int featuresBonus = monster.getMonsterFeature() != null
                ? monster.getMonsterFeature().size() * 10
                : 0;
        return base - featuresBonus;
    }
}
