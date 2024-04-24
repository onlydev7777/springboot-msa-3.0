package com.example.orderservice.dto;

import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order toEntity(OrderDto dto);

  OrderDto toDto(Order entity);
}
