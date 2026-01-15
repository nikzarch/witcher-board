package ru.nikzarch.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.mainservice.domain.battles.Battles;

@Repository
public interface BattlesRepository extends JpaRepository<Battles,Long> {
}
