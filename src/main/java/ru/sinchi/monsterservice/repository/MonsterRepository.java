package ru.sinchi.monsterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinchi.monsterservice.domain.Monster;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Integer> {
}
