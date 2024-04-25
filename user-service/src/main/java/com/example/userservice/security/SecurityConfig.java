package com.example.userservice.security;

import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserService userService;
  private final ObjectMapper objectMapper;
  private final PasswordEncoder passwordEncoder;
  private final Environment env;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);

    authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> auth
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
        )
        .authenticationManager(authenticationManager)
        .addFilter(getAuthenticationFilter(authenticationManager));

    return http.build();
  }

  private AuthenticationFilter getAuthenticationFilter(
      AuthenticationManager authenticationManager) {
    return new AuthenticationFilter(authenticationManager, objectMapper, userService, env);
  }
}
