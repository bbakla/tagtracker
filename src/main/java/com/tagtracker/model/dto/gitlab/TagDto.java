package com.tagtracker.model.dto.gitlab;

public class TagDto {

  private String message;
  private String tagName;
  private String releaseNotes;

  public TagDto() {

  }

  public TagDto(String tagName, String message, String releaseNote) {
    this.message = message;
    this.tagName = tagName;
    this.releaseNotes = releaseNote;
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

  public String getReleaseNotes() {
    return releaseNotes;
  }

  public void setReleaseNotes(String releaseNotes) {
    this.releaseNotes = releaseNotes;
  }
}
