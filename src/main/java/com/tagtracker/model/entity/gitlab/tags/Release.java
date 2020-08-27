package com.tagtracker.model.entity.gitlab.tags;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release {

  @JsonProperty("tag_name")
  private String tagName;

  private String description;

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
