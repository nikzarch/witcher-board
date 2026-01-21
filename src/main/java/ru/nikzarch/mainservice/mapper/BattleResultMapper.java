package ru.nikzarch.mainservice.mapper;

import org.springframework.stereotype.Component;
import ru.nikzarch.mainservice.domain.battles.BattleResult;
import ru.nikzarch.mainservice.domain.battles.dto.BattleResultDTO;

import java.time.LocalDateTime;

@Component
public class BattleResultMapper {

    public BattleResultDTO toDto(BattleResult battleResult) {
        return new BattleResultDTO(battleResult.getOrderId(),battleResult.getWitcherId(),battleResult.getMonsterId(),battleResult.isSuccess(),battleResult.getChance(),"",battleResult.getCreatedAt());
    }
}