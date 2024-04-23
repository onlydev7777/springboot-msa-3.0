package com.example.userservice.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email Not be less than two characters")
  private String email;

  @NotNull(message = "Name cannot be null")
  @Size(min = 2, message = "Name Not be less than two characters")
  private String name;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be less than 8 characters")
  private String pwd;
}
