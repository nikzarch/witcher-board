package ru.nikzarch.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikzarch.mainservice.domain.coordinates.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
