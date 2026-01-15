package ru.nikzarch.mainservice.domain.battles;

import jakarta.persistence.*;
import lombok.Data;
import ru.nikzarch.monsterservice.domain.Monster;

@Entity
@Table(name = "battles")
@Data
public class Battles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long WitcherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id", nullable = false)
    Monster monsterId;

    @Column(name = "battle_success", nullable = false)
    Boolean battleSuccess;
}
