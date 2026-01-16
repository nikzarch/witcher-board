package ru.nikzarch.mainservice.domain.notification.dto;

import ru.nikzarch.witcherboard.domain.user.User;

import java.time.ZonedDateTime;

public record NotificationDTO(
        String message,
        ZonedDateTime createdAt,
        User user
) {}