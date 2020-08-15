package com.tagtracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

/*  @Value("${PRIVATE_TOKEN}")
  private String privateToken;*/

  @Autowired
  private Environment env;

  @Bean
  public WebClient webClient() {

    return WebClient.builder()
        .baseUrl("https://code.siemens.com/api/v4")

        .defaultHeader(
            "Cookie", "mellon-entitlement=" + entitlementValue())
        .defaultHeader("PRIVATE-TOKEN", privateToken())
        .build();
  }

  @Bean
  public String privateToken() {
    return env.getProperty("PRIVATE-TOKEN");
  }

  @Bean
  public String entitlementValue() {
    return env.getProperty("mellon-entitlement");
  }
}

