package com.example.catalogservice.dto;

import com.example.catalogservice.entity.Catalog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

  CatalogDto toDto(Catalog entity);
}
