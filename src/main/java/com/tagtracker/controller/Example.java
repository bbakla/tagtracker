package com.tagtracker.controller;

public class Example {

  private String name;
  private String id;

  public Example(String name, String s) {
    this.name = name;
    this.id = s;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
