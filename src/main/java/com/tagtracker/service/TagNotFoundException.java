package com.tagtracker.service;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class TagNotFoundException extends Exception {

  public TagNotFoundException(WebClientResponseException e) {
    super(e);
  }
}
