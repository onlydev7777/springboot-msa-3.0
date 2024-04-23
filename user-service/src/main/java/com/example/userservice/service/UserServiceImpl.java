package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UsersMapper;
import com.example.userservice.entity.Users;
import com.example.userservice.repository.UsersRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UsersRepository repository;
  private final UsersMapper mapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());
    userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
    repository.save(mapper.toEntity(userDto));
    return userDto;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    Users users = repository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return mapper.toDto(users);
  }

  @Override
  public List<UserDto> getUserByAll() {
    return repository.findAll().stream()
        .map(u -> mapper.toDto(u))
        .toList();
  }
}
