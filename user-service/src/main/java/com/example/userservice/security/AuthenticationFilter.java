package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;
  private final UserService userService;
  private final Environment env;

  public AuthenticationFilter(AuthenticationManager authenticationManager,
      ObjectMapper objectMapper, UserService userService, Environment env) {
    super(authenticationManager);
    this.objectMapper = objectMapper;
    this.userService = userService;
    this.env = env;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
          LoginRequest.class);

      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
          loginRequest.getEmail(), loginRequest.getPassword(),
          new ArrayList<>()
      );

      return getAuthenticationManager().authenticate(token);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String email = ((User) authResult.getPrincipal()).getUsername();
    UserDto userDetails = userService.getUserDetailsByEmail(email);

    byte[] secretKeyBytes = Base64.getEncoder()
        .encode(env.getProperty("token.secret").getBytes());
    SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

    Instant now = Instant.now();
    String token = Jwts.builder()
        .subject(userDetails.getUserId())
        .expiration(
            Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
        .issuedAt(Date.from(now))
        .signWith(secretKey)
        .compact();

    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    response.addHeader("userId", userDetails.getUserId());
  }
}
