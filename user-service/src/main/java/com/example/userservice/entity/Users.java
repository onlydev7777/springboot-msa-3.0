package com.example.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String userId;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50, unique = true)
  private String email;

  @Column(nullable = false)
  private String pwd;

  @Column(nullable = false, unique = true)
  private String encryptedPwd;

  @Builder
  public Users(String userId, String name, String email, String pwd, String encryptedPwd) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.pwd = pwd;
    this.encryptedPwd = encryptedPwd;
  }
}
