package ru.nikzarch.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikzarch.mainservice.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
