package com.example.catalogservice.vo;

import com.example.catalogservice.dto.CatalogDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogDtoMapper {

  CatalogResponse toResponse(CatalogDto dto);
}
