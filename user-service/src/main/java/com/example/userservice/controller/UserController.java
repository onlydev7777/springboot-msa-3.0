package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.UserDtoMapper;
import com.example.userservice.vo.UserRequest;
import com.example.userservice.vo.UserResponse;
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
@RequestMapping
@RestController
public class UserController {

  private final Greeting greeting;
  private final UserService service;
  private final UserDtoMapper mapper;
  private final Environment env;

  @GetMapping("/")
  public String root() {
    return "root";
  }

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
    return ResponseEntity.ok(mapper.toResponse(service.getUserByUserId(userId)));
  }
}
