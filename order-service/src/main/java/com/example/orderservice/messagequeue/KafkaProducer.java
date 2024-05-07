package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public OrderDto send(String topic, OrderDto orderDto) throws JsonProcessingException {
    String jsonString = objectMapper.writeValueAsString(orderDto);
    kafkaTemplate.send(topic, jsonString);
    log.info("Kafka Producer sent data from the Order microservice : " + jsonString);

    return orderDto;
  }
}
