package com.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/first-service")
@RestController
public class FirstServiceController {

  private final Environment env;

  @GetMapping("/welcome")
  public String welcome() {
    return "welcome to the First service!";
  }

  @GetMapping("/message")
  public String message(@RequestHeader("first-request") String header) {
    log.info(header);
    return "Hello World in First Service";
  }

  @GetMapping("/check")
  public String check(HttpServletRequest request) {
    log.info("server port = {}", request.getServerPort());

    return String.format("Hi, there. THis is a messsage from First Service on Port %s"
        , env.getProperty("local.server.port"));
  }
}
