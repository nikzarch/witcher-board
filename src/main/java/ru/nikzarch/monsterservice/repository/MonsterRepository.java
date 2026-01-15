package ru.nikzarch.monsterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.monsterservice.domain.Monster;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
}
