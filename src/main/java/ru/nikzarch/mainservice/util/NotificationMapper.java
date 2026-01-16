package ru.nikzarch.mainservice.util;

import org.springframework.stereotype.Component;
import ru.nikzarch.mainservice.domain.notification.Notification;
import ru.nikzarch.mainservice.domain.notification.dto.NotificationDTO;

@Component
public class NotificationMapper {

    public NotificationDTO toDto(Notification notification) {
        return new NotificationDTO(
                notification.getMessage(),
                notification.getCreatedAt(),
                notification.getUser()
        );
    }
}
