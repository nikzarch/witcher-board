package ru.nikzarch.monsterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikzarch.monsterservice.domain.MonsterFeature;
import ru.nikzarch.monsterservice.repository.MonsterFeatureRepository;
import ru.nikzarch.monsterservice.service.MonsterFeatureService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MonsterFeatureServiceImpl implements MonsterFeatureService {
    private final MonsterFeatureRepository monsterFeatureRepository;

    @Override
    public MonsterFeature getMonsterFeatureById(Integer id) {
        return monsterFeatureRepository.findById(id).orElseThrow(()-> new RuntimeException("feature not found"));
    }

    @Override
    public List<MonsterFeature> getAll() {
        return monsterFeatureRepository.findAll();
    }
}
