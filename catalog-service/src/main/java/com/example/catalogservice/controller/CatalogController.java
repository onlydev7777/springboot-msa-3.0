package com.example.catalogservice.controller;

import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.CatalogDtoMapper;
import com.example.catalogservice.vo.CatalogResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/catalog-service")
@RestController
public class CatalogController {

  private final CatalogService service;
  private final CatalogDtoMapper mapper;
  private final Environment env;

  @GetMapping("/health-check")
  public String status() {
    return String.format("It's Working in Catalog Service on Port %s",
        env.getProperty("local.server.port"));
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<CatalogResponse>> getAll() {
    List<CatalogResponse> responseList = service.getAllCatalogs().stream()
        .map(dto -> mapper.toResponse(dto))
        .toList();

    return ResponseEntity.ok(responseList);
  }
}
