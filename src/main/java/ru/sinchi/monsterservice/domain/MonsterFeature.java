package ru.sinchi.monsterservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "monster_feature")
@Data
public class MonsterFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
