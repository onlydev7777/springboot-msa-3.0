package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderMapper;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  private final OrderMapper mapper;

  @Override
  @Transactional
  public OrderDto createOrder(OrderDto dto) {
    dto.setOrderId(UUID.randomUUID().toString());
    dto.setTotalPrice(dto.getQty() * dto.getUnitPrice());
    dto.setCreatedAt(LocalDate.now());

    Order order = mapper.toEntity(dto);

    return mapper.toDto(repository.save(order));
  }

  @Override
  public List<OrderDto> getOrdersByUserId(String userId) {
    return repository.findAllByUserId(userId).stream()
        .map(o -> mapper.toDto(o))
        .toList();
  }

  @Override
  public OrderDto getOrderByOrderId(String orderId) {
    return mapper.toDto(repository.findByOrderId(orderId)
        .orElseThrow(() -> new EntityNotFoundException()));
  }
}
