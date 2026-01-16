package ru.nikzarch.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikzarch.mainservice.domain.notification.dto.NotificationDTO;
import ru.nikzarch.mainservice.service.impl.NotificationServiceImpl;
import ru.nikzarch.witcherboard.domain.user.User;
import ru.nikzarch.witcherboard.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            @PathVariable Long userId
    ) {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(notificationService.getUserNotifications(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearNotifications(
            @PathVariable Long userId
    ) {
        User user = userService.findUserById(userId);
        notificationService.clearUserNotifications(user);
        return ResponseEntity.ok().build();
    }
}
