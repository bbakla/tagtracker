package com.tagtracker.model.resource;

import com.tagtracker.model.entity.tracker.Auditable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProjectResource extends Auditable<String> implements Serializable {

  private String projectId;
  private String projectName;
  private String encodedPath;
  private String description;
  private Set<TagResource> tags = new HashSet<>();


  public ProjectResource() {
  }

  public ProjectResource(String id, String projectName) {
    this.projectId = id;
    this.projectName = projectName;
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

  public void setProjectId(String id) {
    this.projectId = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEncodedPath() {
    return encodedPath;
  }

  public void setEncodedPath(String encodedPath) {
    this.encodedPath = encodedPath;
  }

  public Set<TagResource> getTags() {
    return tags;
  }

  public void setTags(Set<TagResource> tags) {
    this.tags = tags;
  }
}
