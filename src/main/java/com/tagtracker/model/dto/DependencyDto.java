package com.tagtracker.model.dto;

public class DependencyDto {

  private String projectName;

  private String tagName;

  private String projectId;

  public DependencyDto(String projectName, String projectId, String tagName) {
    this.projectName = projectName;
    this.projectId = projectId;
    this.tagName = tagName;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

}
