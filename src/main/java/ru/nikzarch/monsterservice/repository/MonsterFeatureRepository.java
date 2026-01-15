package ru.nikzarch.monsterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.monsterservice.domain.MonsterFeature;

@Repository
public interface MonsterFeatureRepository extends JpaRepository<MonsterFeature,Integer> {
}
