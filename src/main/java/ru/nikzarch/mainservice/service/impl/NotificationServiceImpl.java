package ru.nikzarch.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikzarch.mainservice.domain.notification.Notification;
import ru.nikzarch.mainservice.domain.notification.dto.NotificationDTO;
import ru.nikzarch.mainservice.domain.order.Order;
import ru.nikzarch.mainservice.repository.NotificationRepository;
import ru.nikzarch.mainservice.service.NotificationService;
import ru.nikzarch.mainservice.util.NotificationMapper;
import ru.nikzarch.witcherboard.domain.user.User;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> getUserNotifications(User user) {
        return notificationRepository.findAllByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @Transactional
    public Notification createNotification(User user, String message) {
        Notification notification = new Notification(
                null,
                message,
                user,
                ZonedDateTime.now()
        );

        return notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void clearUserNotifications(User user) {
        notificationRepository.deleteAllByUser(user);
    }
}
