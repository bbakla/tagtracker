package com.tagtracker.model.resource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TagResource {

  private String projectId;
  private String projectName;
  private String message;
  private String tagName;
  private String releaseNotes;
  private Set<TagResource> tagsDependentOn = new HashSet();
  private Set<TagResource> tagsDependentOnMe = new HashSet();
  private Map<String, JobResource> deployedEnvironments = new HashMap<>();


  public TagResource(String projectId, String projectName, String tagName, String message,
      String releaseNotes) {
    this.projectId = projectId;
    this.projectName = projectName;
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

  public void setTagsDependentOnMe(Set<TagResource> tagsDependentOnMe) {
    this.tagsDependentOnMe = tagsDependentOnMe;
  }

  public Map<String, JobResource> getDeployedEnvironments() {
    return deployedEnvironments;
  }

  public void setDeployedEnvironments(
      Map<String, JobResource> deployedEnvironments) {
    this.deployedEnvironments = deployedEnvironments;
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
}


