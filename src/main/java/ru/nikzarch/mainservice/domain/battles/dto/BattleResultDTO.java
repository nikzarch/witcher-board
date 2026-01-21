package ru.nikzarch.mainservice.domain.battles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BattleResultDTO {
    private Long orderId;
    private Long witcherId;
    private Long monsterId;
    private boolean success;
    private double chance;
    private String message;
    private LocalDateTime createdAt;

    public BattleResultDTO() {

    }
}
