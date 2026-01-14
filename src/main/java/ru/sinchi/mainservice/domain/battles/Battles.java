package ru.sinchi.mainservice.domain.battles;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "battles")
@Data
public class Battles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long WitcherId;

    // Пока что нет таблицы
    @Column(name = "monster_id", nullable = false)
    Long monsterId;

    @Column(name = "battle_success", nullable = false)
    Boolean battleSuccess;
}
