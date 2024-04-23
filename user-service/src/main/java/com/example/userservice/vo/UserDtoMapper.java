package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

  UserDto toDto(UserRequest request);

  UserResponse toResponse(UserDto dto);
}
