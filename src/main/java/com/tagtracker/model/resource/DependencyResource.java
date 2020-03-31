package com.tagtracker.model.resource;

public class DependencyResource {

  private String projectId;
  private String projectName;
  private String projectPath;
  private TagResource tagResource;

  public DependencyResource(String projectId, String projectName, String projectPath,
      TagResource tagResource) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.projectPath = projectPath;
    this.tagResource = tagResource;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectPath() {
    return projectPath;
  }

  public void setProjectPath(String projectPath) {
    this.projectPath = projectPath;
  }

  public TagResource getTagResource() {
    return tagResource;
  }

  public void setTagResource(TagResource tagResource) {
    this.tagResource = tagResource;
  }
}
