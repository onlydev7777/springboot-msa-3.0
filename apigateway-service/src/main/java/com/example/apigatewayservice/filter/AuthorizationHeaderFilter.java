package com.example.apigatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

  private final Environment env;

  public AuthorizationHeaderFilter(Environment env) {
    super(Config.class);
    this.env = env;
  }

  @Override
  public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
    // AuthorizationHeaderFilter Pre Filter
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authorizationHeader.replace("Bearer ", "");

      if (!isJwtValid(jwt)) {
        return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
      }

      return chain.filter(exchange);
    });
  }

  private boolean isJwtValid(String jwt) {
    byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());
    SecretKey signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

    String subject = null;

    try {
      JwtParser parser = Jwts.parser()
          .verifyWith(signingKey)
          .build();

      subject = parser
          .parseSignedClaims(jwt)
          .getPayload()
          .getSubject();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }

    if (subject == null || subject.isEmpty()) {
      return false;
    }

    return true;
  }

  private Mono<Void> onError(ServerWebExchange exchange, String error,
      HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    log.error(error);
    return response.setComplete();
  }

  public static class Config {

  }
}
