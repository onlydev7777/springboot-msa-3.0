package com.example.userservice.controller;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.OrderResponse;
import com.example.userservice.vo.UserDtoMapper;
import com.example.userservice.vo.UserRequest;
import com.example.userservice.vo.UserResponse;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class UserController {

  private final Greeting greeting;
  private final UserService service;
  private final UserDtoMapper mapper;
  private final Environment env;
  private final RestTemplate restTemplate;
  private final OrderServiceClient orderServiceClient;
  private final CircuitBreakerFactory circuitBreakerFactory;

  @GetMapping("/")
  public String root() {
    return "root";
  }

  @Timed(value = "users.status", longTask = true)
  @GetMapping("/health-check")
  public String status() {
    return String.format("It's Working in User Service"
        + ", port(local.server.port)=" + env.getProperty("local.server.port")
        + ", port(server.port)=" + env.getProperty("server.port")
        + ", gateway ip(env)=" + env.getProperty("gateway.ip")
        + ", message=" + env.getProperty("greeting.message")
        + ", token secret=" + env.getProperty("token.secret")
        + ", token expiration time=" + env.getProperty("token.expiration_time"));
  }

  @Timed(value = "users.welcome", longTask = true)
  @GetMapping("/welcome")
  public String welcome() {
    return greeting.getMessage();
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
    UserDto userDto = service.createUser(mapper.toDto(request));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toResponse(userDto));
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> getAll() {
    List<UserResponse> responseList = service.getUserByAll().stream()
        .map(dto -> mapper.toResponse(dto))
        .toList();

    return ResponseEntity.ok(responseList);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<UserResponse> getUserByUserId(@PathVariable String userId) {
    UserDto userDto = service.getUserByUserId(userId);
    UserResponse userResponse = mapper.toResponse(userDto);

    String orderUrl = String.format(env.getProperty("order-service.url"), userId);

    // rest template
//    ResponseEntity<List<OrderResponse>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//        new ParameterizedTypeReference<>() {
//        });
//    List<OrderResponse> orderResponses = orderListResponse.getBody();

    // feign client error handling
//    List<OrderResponse> orderResponses = new ArrayList<>();
//    try {
//      orderResponses = orderServiceClient.getOrders(userId);
//    } catch (FeignClientException fce) {
//      log.error(fce.getMessage());
//    }

    // ErrorDecoder
//    List<OrderResponse> orderResponses = orderServiceClient.getOrders(userId);
    log.info("Before call orders microservice");
    CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
    List<OrderResponse> orderResponses = circuitbreaker.run(() -> orderServiceClient.getOrders(userId),
        throwable -> new ArrayList<>());
    log.info("After call orders microservice");
    userResponse.setOrders(orderResponses);

    return ResponseEntity.ok(userResponse);
  }
}
