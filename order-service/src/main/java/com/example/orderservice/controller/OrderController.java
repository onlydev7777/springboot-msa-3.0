package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.OrderDtoMapper;
import com.example.orderservice.vo.OrderRequest;
import com.example.orderservice.vo.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {

  private final OrderService service;
  private final OrderDtoMapper mapper;
  private final Environment env;


  @GetMapping("/health-check")
  public String status() {
    return String.format("It's Working in Order Service on Port %s",
        env.getProperty("local.server.port"));
  }

  @PostMapping("/{userId}/orders")
  public ResponseEntity<OrderResponse> createOrder(@PathVariable String userId,
      @RequestBody OrderRequest request) {

    OrderDto dto = mapper.toDto(request);
    dto.setUserId(userId);
    dto = service.createOrder(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toResponse(dto));
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable String userId) {
    return ResponseEntity.ok(
        service.getOrdersByUserId(userId).stream()
            .map(dto -> mapper.toResponse(dto))
            .toList()
    );
  }

}
