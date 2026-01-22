package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.coordinates.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAll();

    Location getById(Long id);

    Location create(Location location);
}
