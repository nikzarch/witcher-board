package ru.nikzarch.monsterservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import ru.nikzarch.mainservice.domain.coordinates.Location;

import java.util.Set;

@Entity
@Table(name = "monster")
@Data
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "danger_level")
    private Byte dangerLevel;

    @ManyToMany
    @JoinTable(
            name = "location_monster",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    Set<Location> location;

    @ManyToMany
    @JoinTable(
            name = "monster_to_feature",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "monster_feature_id")
    )
    Set<MonsterFeature> monsterFeature;
}
