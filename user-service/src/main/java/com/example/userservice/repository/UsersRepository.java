package com.example.userservice.repository;

import com.example.userservice.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUserId(String userId);
}
