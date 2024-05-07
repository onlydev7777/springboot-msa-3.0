package com.example.catalogservice.messagequeue;

import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

  private final CatalogRepository repository;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "example-catalog-topic")
  @Transactional
  public void updateQty(String kafkaMessage) throws JsonProcessingException {
    log.info("Kafka Message : " + kafkaMessage);

    Map<String, Object> map = objectMapper.readValue(kafkaMessage, Map.class);

    Catalog catalog = repository.findByProductId((String) map.get("productId"))
        .orElseThrow();

    catalog.decreaseStock((Integer) map.get("qty"));
  }
}
