package com.example.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120, unique = true)
  private String productId;
  @Column(nullable = false)
  private Integer qty;
  @Column(nullable = false)
  private Integer unitPrice;

  @Column(nullable = false)
  private String userId;
  @Column(nullable = false, unique = true)
  private String orderId;

  @Column(nullable = false, updatable = false)
  private LocalDate createdAt;

  @Builder
  public Order(String productId, Integer qty, Integer unitPrice, String userId, String orderId,
      LocalDate createdAt) {
    this.productId = productId;
    this.qty = qty;
    this.unitPrice = unitPrice;
    this.userId = userId;
    this.orderId = orderId;
    this.createdAt = createdAt;
  }
}
