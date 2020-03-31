package com.tagtracker.service;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ProjectNotFoundException extends Exception {

  public ProjectNotFoundException(String message) {
    super(message);
  }

  public ProjectNotFoundException(WebClientResponseException e) {
    super(e);
  }
}
