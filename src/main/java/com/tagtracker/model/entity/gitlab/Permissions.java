package com.tagtracker.model.entity.gitlab;

public class Permissions {

  Project_access Project_accessObject;
  private String group_access = null;

  // Getter Methods

  public Project_access getProject_access() {
    return Project_accessObject;
  }

  public String getGroup_access() {
    return group_access;
  }

  // Setter Methods

  public void setProject_access(Project_access project_accessObject) {
    this.Project_accessObject = project_accessObject;
  }

  public void setGroup_access(String group_access) {
    this.group_access = group_access;
  }
}
