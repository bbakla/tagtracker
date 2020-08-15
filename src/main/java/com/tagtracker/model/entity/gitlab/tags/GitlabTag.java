package com.tagtracker.model.entity.gitlab.tags;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitlabTag {

  private String name;
  private String message;
  private String target;

  private Commit commitObject;

  //@JsonIgnore
  private Release release;

  @JsonProperty("protected")
  private boolean isProtected;

  public String getName() {
    return name;
  }

  public String getMessage() {
    return message;
  }

  public String getTarget() {
    return target;
  }

  public Commit getCommit() {
    return commitObject;
  }

  public Release getRelease() {
    return release;
  }

  public boolean getProtected() {
    return isProtected;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setCommit(Commit commitObject) {
    this.commitObject = commitObject;
  }

  public void setRelease(Release release) {
    this.release = release;
  }

  public void setProtected(boolean isProtected) {
    this.isProtected = isProtected;
  }

  public String getCommitDate() {
    return commitObject.getCreated_at();
  }
}
