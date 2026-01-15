package ru.sinchi.monsterservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sinchi.monsterservice.domain.MonsterFeature;
import ru.sinchi.monsterservice.repository.MonsterFeatureRepository;
import ru.sinchi.monsterservice.service.MonsterFeatureService;

@RequiredArgsConstructor
@Service
public class MonsterFeatureServiceImpl implements MonsterFeatureService {
    private final MonsterFeatureRepository monsterFeatureRepository;

    @Override
    public MonsterFeature getMonsterFeatureById(Integer id) {
        return monsterFeatureRepository.findById(id).orElseThrow(()-> new RuntimeException("feature not found"));
    }
}
