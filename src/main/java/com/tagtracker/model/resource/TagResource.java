package com.tagtracker.model.resource;

import com.tagtracker.model.entity.Environment;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TagResource {

  private String projectId;
  private String message;
  private String tagName;
  private String releaseNotes;
  private Set<TagResource> tagsDependentOn = new HashSet();
  private Set<TagResource> tagsDependentOnMe = new HashSet();
  private Map<Environment, Boolean> deployedEnvironments = new EnumMap<>(Environment.class);


  public TagResource(String projectId, String tagName, String message, String releaseNotes) {
    this.projectId = projectId;
    this.message = message;
    this.tagName = tagName;
    this.releaseNotes = releaseNotes;

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

  public Set<TagResource> getTagsDependentOn() {
    return tagsDependentOn;
  }

  public void setTagsDependentOn(Set<TagResource> tagsDependentOn) {
    this.tagsDependentOn = tagsDependentOn;
  }

  public Set<TagResource> getTagsDependentOnMe() {
    return tagsDependentOnMe;
  }

  public void setTagsDependentOnMe(
      Set<TagResource> tagsDependentOnMe) {
    this.tagsDependentOnMe = tagsDependentOnMe;
  }

  public Map<Environment, Boolean> getDeployedEnvironments() {
    return deployedEnvironments;
  }

  public void setDeployedEnvironments(
      Map<Environment, Boolean> deployedEnvironments) {
    this.deployedEnvironments = deployedEnvironments;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
}
