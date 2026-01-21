package ru.nikzarch.monsterservice.service;

import ru.nikzarch.monsterservice.domain.MonsterFeature;

import java.util.List;

public interface MonsterFeatureService {
    MonsterFeature getMonsterFeatureById(Integer id);
    List<MonsterFeature> getAll();
}
