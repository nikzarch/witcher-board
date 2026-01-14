package ru.sinchi.mainservice.domain.order;

import jakarta.persistence.*;
import lombok.Data;
import ru.sinchi.mainservice.domain.coordinates.Location;

import java.time.ZonedDateTime;

@Entity
@Table(name = "order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    // Пока что нет самой таблицы
    @Column
    Long monsterId;

    @Column
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    Location locationId;

    @Column
    Integer reward;

    @Column(name = "created_at")
    ZonedDateTime createdAt;

    @Column(name = "odred_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Column(name = "user_id")
    Long UserId;

}
