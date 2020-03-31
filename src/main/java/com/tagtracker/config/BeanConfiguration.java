package com.tagtracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

  @Value("${PRIVATE_TOKEN}")
  private String privateToken;

  @Bean
  public WebClient webClient() {
    return
        WebClient.builder()
            .baseUrl("https://code.siemens.com/api/v4")
            .defaultHeader("PRIVATE-TOKEN", privateToken)
            .build();
  }
}
