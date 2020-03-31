package com.tagtracker.model.dto.gitlab;

import org.aspectj.bridge.Message;

public class TagDto {

  private String message;
  private String tagName;
  private String releaseNote;

  public TagDto(String tagName, String message, String releaseNote) {
    this.message = message;
    this.tagName = tagName;
    this.releaseNote = releaseNote;
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

  public String getReleaseNote() {
    return releaseNote;
  }

  public void setReleaseNote(String releaseNote) {
    this.releaseNote = releaseNote;
  }
}
