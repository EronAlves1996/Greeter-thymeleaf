package com.eronalves.greeter;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GreeterApplication {

  public static void main (String[] args) {
    SpringApplication.run(GreeterApplication.class, args);
  }

  @Bean
  public Clock clock () {
    return Clock.systemDefaultZone();
  }
}
