package com.tagtracker.model.resource;

import com.tagtracker.model.resource.TagResource;
import com.tagtracker.model.entity.Auditable;
import com.tagtracker.model.entity.Environment;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.web.util.UriUtils;

public class ApplicationResource extends Auditable<String> implements Serializable {

  private String projectId;

  private String encodedPath;

  private TagResource tag;

  private String applicationName;

  private Map<Environment, Boolean> deployedEnvironments = new EnumMap<>(Environment.class);

  private Set<DependencyResource> dependentToMe = new HashSet();

  private Set<DependencyResource> dependentTo = new HashSet();

  public ApplicationResource() {
  }

  public ApplicationResource(String id, String applicationName) {
    this.projectId = id;
    this.applicationName = applicationName;
  }


  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String serviceName) {
    this.applicationName = serviceName;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String id) {
    this.projectId = id;
  }

  public Map<Environment, Boolean> getDeployedEnvironments() {
    return deployedEnvironments;
  }

  public void setDeployedEnvironments(Map<Environment, Boolean> deployedEnvironments) {
    this.deployedEnvironments = deployedEnvironments;
  }

  public void deployedTo(Environment environment) {
    this.deployedEnvironments.put(environment, true);
  }

  public Set<DependencyResource> getDependentToMe() {
    return dependentToMe;
  }

  public void setDependentToMe(Set<DependencyResource> dependentToMe) {
    this.dependentToMe = dependentToMe;
  }

  public void addDependentOnMe(DependencyResource dependentToMe) {
    this.dependentToMe.add(dependentToMe);
  }

  public void removeDependentApplication(DependencyResource dependentToMe) {
    this.dependentToMe.remove(dependentToMe);
  }

  public Set<DependencyResource> getDependentTo() {
    return dependentTo;
  }

  public void setDependentTo(Set<DependencyResource> dependentTo) {
    this.dependentTo = dependentTo;
  }

  public void addDependency(DependencyResource dependentTo) {
    this.dependentTo.add(dependentTo);
  }

  public void removeDependency(DependencyResource dependentTo) {
    this.dependentTo.remove(dependentTo);
  }

  public TagResource getTag() {
    return tag;
  }

  public void setTag(TagResource tag) {
    this.tag = tag;
  }

  public String getEncodedPath() {
    return encodedPath;
  }

  public void setEncodedPath(String encodedPath) {
    this.encodedPath = encodedPath;
  }
}
