package ru.nikzarch.mainservice.domain.order.dto;

import lombok.Data;
import ru.nikzarch.mainservice.domain.order.OrderStatus;

import java.time.ZonedDateTime;

public record OrderDTO (
        Long id,
        String name,
        Long monsterId,
        String description,
        Long locationId,
        Integer reward,
        ZonedDateTime createdAt,
        OrderStatus orderStatus,
        Long userId
){

}