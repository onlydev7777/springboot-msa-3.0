package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.UserDtoMapper;
import com.example.userservice.vo.UserRequest;
import com.example.userservice.vo.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/user-service/health-check")
  public String status() {
    return String.format("It's Working in User Service on Port %s",
        env.getProperty("local.server.port"));
  }

  @GetMapping("/user-service/welcome")
  public String welcome() {
    return greeting.getMessage();
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
    UserDto userDto = service.createUser(mapper.toDto(request));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(mapper.toResponse(userDto));
  }
}
