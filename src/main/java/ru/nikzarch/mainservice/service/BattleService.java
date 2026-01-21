package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.battles.dto.BattleResultDTO;

public interface BattleService {
    BattleResultDTO fight(Long orderId, Long witcherId);
    java.util.List<BattleResultDTO> getHistory(Long witcherId);
}

