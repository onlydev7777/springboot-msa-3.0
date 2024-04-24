package com.example.orderservice.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OrderDto {

  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;
  private LocalDate createdAt;

  private String orderId;
  private String userId;
}