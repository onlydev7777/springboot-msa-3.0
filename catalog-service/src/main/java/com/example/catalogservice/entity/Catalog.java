package com.example.catalogservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "catalog")
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

  @Column(nullable = false, updatable = false, insertable = false)
  @CreatedDate
  private LocalDate createdAt;
}

