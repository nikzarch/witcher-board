package ru.nikzarch.mainservice.domain.battles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikzarch.monsterservice.domain.Monster;

@Entity
@Table(name = "battles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long WitcherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monster_id", nullable = false)
    Monster monsterId;

    @Column(name = "battle_success", nullable = false)
    Boolean battleSuccess;
}
