package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.OrderDtoMapper;
import com.example.orderservice.vo.OrderRequest;
import com.example.orderservice.vo.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {

  private final OrderService service;
  private final KafkaProducer kafkaProducer;
  private final OrderProducer orderProducer;
  private final OrderDtoMapper mapper;
  private final Environment env;


  @GetMapping("/health-check")
  public String status() {
    return String.format("It's Working in Order Service on Port %s",
        env.getProperty("local.server.port"));
  }

  @PostMapping("/{userId}/orders")
  public ResponseEntity<OrderResponse> createOrder(@PathVariable String userId,
      @RequestBody OrderRequest request) throws JsonProcessingException {
    log.info("Before add orders data");
    OrderDto dto = mapper.toDto(request);
    dto.setUserId(userId);
    dto.setTotalPrice(dto.getQty() * dto.getUnitPrice());
    dto.setCreatedAt(LocalDate.now());
    dto.setOrderId(UUID.randomUUID().toString());
    dto = service.createOrder(dto);

    //send kafka
//    kafkaProducer.send("example-catalog-topic", dto);
//    orderProducer.send("orders", dto);

    OrderResponse response = mapper.toResponse(dto);

    log.info("After added orders data");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable String userId) {
    log.info("Before retrieve orders data");

    List<OrderResponse> responses = service.getOrdersByUserId(userId).stream()
        .map(dto -> mapper.toResponse(dto))
        .toList();

    log.info("After retrieved orders data");

    return ResponseEntity.ok(responses);
  }

}
