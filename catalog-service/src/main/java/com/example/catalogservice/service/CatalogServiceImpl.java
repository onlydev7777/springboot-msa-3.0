package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.dto.CatalogMapper;
import com.example.catalogservice.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

  private final CatalogRepository repository;
  private final CatalogMapper mapper;

  @Override
  public List<CatalogDto> getAllCatalogs() {
    return repository.findAll().stream()
        .map(c -> mapper.toDto(c))
        .toList();
  }
}
