package com.example.userservice.dto;

import com.example.userservice.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

  Users toEntity(UserDto dto);

  UserDto toDto(Users entity);
}
