package com.tagtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class TagtrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TagtrackerApplication.class, args);
  }

}
