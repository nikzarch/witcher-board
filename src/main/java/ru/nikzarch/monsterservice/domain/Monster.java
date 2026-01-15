package ru.nikzarch.monsterservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikzarch.mainservice.domain.coordinates.Location;

import java.util.Set;

@Entity
@Table(name = "monster")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
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
