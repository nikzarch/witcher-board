package ru.nikzarch.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.mainservice.domain.notification.Notification;
import ru.nikzarch.witcherboard.domain.user.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserOrderByCreatedAtDesc(User user);

    void deleteAllByUser(User user);
}
