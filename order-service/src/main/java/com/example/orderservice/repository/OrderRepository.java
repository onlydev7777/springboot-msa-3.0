package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByOrderId(String orderId);

  List<Order> findAllByUserId(String userId);

}
