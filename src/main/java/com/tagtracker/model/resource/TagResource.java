package com.tagtracker.model.resource;

public class TagResource {

  private String message;
  private String tagName;

  public TagResource(String tagName, String message) {
    this.message = message;
    this.tagName = tagName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }
}
