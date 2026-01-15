package ru.nikzarch.monsterservice.service;

import ru.nikzarch.monsterservice.domain.Monster;

import java.util.List;

public interface MonsterService {

    Monster createMonster(Monster monster);

    Monster updateMonster(Long id, Monster monster);

    Monster getById(Long id);

    List<Monster> getAll();

    int calculateRecommendedReward(Monster monster);
}
