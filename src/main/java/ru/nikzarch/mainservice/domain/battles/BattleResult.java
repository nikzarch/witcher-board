package ru.nikzarch.mainservice.domain.battles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "battle_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long witcherId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long monsterId;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private double chance;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
