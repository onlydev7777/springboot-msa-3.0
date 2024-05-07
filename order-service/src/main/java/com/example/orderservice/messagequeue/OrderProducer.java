package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.Field;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.Payload;
import com.example.orderservice.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {

  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private List<Field> fields = Arrays.asList(new Field("string", true, "order_id"),
      new Field("string", true, "user_id"),
      new Field("string", true, "product_id"),
      new Field("int32", true, "qty"),
      new Field("int32", true, "unit_price"),
      new Field("int32", true, "total_price")
  );

  private Schema schema = Schema.builder()
      .type("struct")
      .fields(fields)
      .optional(false)
      .name("orders")
      .build();

  public OrderDto send(String topic, OrderDto orderDto) throws JsonProcessingException {
    Payload payload = Payload.builder()
        .orderId(orderDto.getOrderId())
        .userId(orderDto.getUserId())
        .productId(orderDto.getProductId())
        .qty(orderDto.getQty())
        .unitPrice(orderDto.getUnitPrice())
        .totalPrice(orderDto.getTotalPrice())
        .build();

    KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

    String jsonString = objectMapper.writeValueAsString(kafkaOrderDto);
    kafkaTemplate.send(topic, jsonString);
    log.info("Kafka Producer sent data from the Order microservice : " + jsonString);

    return orderDto;
  }
}
