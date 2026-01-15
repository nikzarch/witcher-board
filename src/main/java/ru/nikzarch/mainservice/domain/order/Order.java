package ru.nikzarch.mainservice.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ru.nikzarch.mainservice.domain.coordinates.Location;
import ru.nikzarch.monsterservice.domain.Monster;

import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monster_id")
    Monster monster;

    @Column
    String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    Location location;

    @Column
    Integer reward;

    @Column(name = "created_at")
    ZonedDateTime createdAt;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Column(name = "user_id")
    Long userId;

}
