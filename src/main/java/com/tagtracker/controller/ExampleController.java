package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.APPLICATION_BASE_PATH;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

  @GetMapping("/api/example")
  public List<Example> getExample() {

    System.out.println("Hereeeeeee");
    return List.of(new Example("name", "1"),
        new Example("name2", "2"), new Example("name3", "3"));
  }
}
