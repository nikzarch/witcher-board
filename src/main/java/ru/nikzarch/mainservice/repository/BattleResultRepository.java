package ru.nikzarch.mainservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikzarch.mainservice.domain.battles.BattleResult;

import java.util.List;

public interface BattleResultRepository extends JpaRepository<BattleResult, Long> {
    List<BattleResult> findByWitcherIdOrderByCreatedAtDesc(Long witcherId);
}
