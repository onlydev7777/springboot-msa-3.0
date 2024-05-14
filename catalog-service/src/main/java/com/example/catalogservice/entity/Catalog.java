package com.example.catalogservice.entity;

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
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "catalogs")
@Entity
public class Catalog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120, unique = true)
  private String productId;
  @Column(nullable = false)
  private String productName;
  @Column(nullable = false)
  private Integer stock;
  @Column(nullable = false)
  private Integer unitPrice;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDate createdAt;

  @Builder
  public Catalog(String productId, String productName, Integer stock, Integer unitPrice,
      LocalDate createdAt) {
    this.productId = productId;
    this.productName = productName;
    this.stock = stock;
    this.unitPrice = unitPrice;
    this.createdAt = createdAt;
  }

  public void decreaseStock(Integer qty) {
    this.stock = this.stock - qty;
  }
}

