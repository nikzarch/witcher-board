package ru.nikzarch.mainservice.service;

import ru.nikzarch.mainservice.domain.notification.Notification;
import ru.nikzarch.mainservice.domain.notification.dto.NotificationDTO;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.witcherboard.domain.user.User;

import java.util.List;

public interface NotificationService {

    List<NotificationDTO> getUserNotifications(User user);

    void clearUserNotifications(User user);

    Notification createNotification(User user, String message);

}
