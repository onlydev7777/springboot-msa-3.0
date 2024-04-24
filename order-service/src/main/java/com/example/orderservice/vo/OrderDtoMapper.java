package com.example.orderservice.vo;

import com.example.orderservice.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

  OrderResponse toResponse(OrderDto dto);

  OrderDto toDto(OrderRequest request);
}
