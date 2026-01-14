package ru.sinchi.mainservice.domain.coordinates;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "location")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double x;

    @Column
    private Double y;

    @Column
    private String name;

    @Column
    private String description;





}
