package com.example.userservice.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OrderResponse {

  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;
  private LocalDate createdAt;
  private String orderId;
}
