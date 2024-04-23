package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UsersMapper;
import com.example.userservice.repository.UsersRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
}
