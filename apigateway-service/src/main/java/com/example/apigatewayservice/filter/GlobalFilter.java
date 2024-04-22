package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

  public GlobalFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    // Global Pre Filter
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Global Pre Filter baseMessage : {}", config.getBaseMessage());

      if(config.isPreLogger()){
        log.info("Global Pre Filter : request id -> {}", request.getId());
      }

      // Global Post Filter
      return chain.filter(exchange).then(Mono.fromRunnable(()->{
        if(config.isPostLogger()){
          log.info("Global Post Filter : response code -> {}", response.getStatusCode());
        }
      }));
    });
  }

  @Data
  public static class Config {
    // Put the configuration properties
    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;
  }
}
