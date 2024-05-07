package com.example.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {

  @JsonProperty("order_id")
  private String orderId;
  @JsonProperty("user_id")
  private String userId;
  @JsonProperty("product_id")
  private String productId;
  private int qty;
  @JsonProperty("unit_price")
  private int unitPrice;
  @JsonProperty("total_price")
  private int totalPrice;
}
