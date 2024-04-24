package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class OrderResponse {

  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;
  private LocalDate createdAt;

  private String orderId;
  private String userId;
}