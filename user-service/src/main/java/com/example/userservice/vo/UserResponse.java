package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserResponse {

  private String email;
  private String name;
  private String userId;

  private List<OrderResponse> orders = new ArrayList<>();
}
