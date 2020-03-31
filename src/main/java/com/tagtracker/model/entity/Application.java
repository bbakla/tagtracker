package com.tagtracker.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;
import org.springframework.web.util.UriUtils;

@Entity
@Table
public class Application extends Auditable<java.lang.String> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // @NaturalId
  @Column(name = "project_id", nullable = false)
  private String projectId;

  @Column(name = "encoded_path", nullable = true)
  private String encodedPath;

  @OneToOne(mappedBy = "application", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  private Tag tag;

  //	@EmbeddedId
  //	private ApplicationIdentifier identifier;

  @NotNull
  @Column(name = "application_name")
  private java.lang.String applicationName;

  @MapKeyEnumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "ENV")
  @CollectionTable(name = "DEPLOYED_ENV")
  @Column(name = "IS_DEPLOYED")
  private Map<Environment, Boolean> deployedEnvironments = new EnumMap<>(Environment.class);

  @ManyToMany(fetch = FetchType.EAGER)
  @JsonBackReference
  private Set<Application> dependentToMe = new HashSet();

  @ManyToMany(mappedBy = "dependentToMe", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Application> dependentTo = new HashSet();

  public Application() {
  }

  public Application(java.lang.String id, java.lang.String applicationName) {
    this.projectId = id;
    this.applicationName = applicationName;
  }

  @PrePersist
  public void prePersist() {
    this.encodedPath = UriUtils.encode(this.encodedPath, "UTF-8");
  }

  @PreUpdate
  public void preUpdate() {
    this.encodedPath = UriUtils.encode(this.encodedPath, "UTF-8");
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public java.lang.String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(java.lang.String serviceName) {
    this.applicationName = serviceName;
  }

  public java.lang.String getProjectId() {
    return projectId;
  }

  public void setProjectId(java.lang.String id) {
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

  public Set<Application> getDependentOnMe() {
    return dependentToMe;
  }

  public void setDependentToMe(Set<Application> dependentToMe) {
    this.dependentToMe = dependentToMe;
  }

  public void addDependencyToMe(Application dependentToMe) {
    this.dependentToMe.add(dependentToMe);
  }

  public void removeDependentApplication(Application dependentToMe) {
    this.dependentToMe.remove(dependentToMe);
  }

  public Set<Application> getDependentTo() {
    return dependentTo;
  }

  public void setDependentTo(Set<Application> dependentTo) {
    this.dependentTo = dependentTo;
  }

  public void addDependency(Application dependentTo) {
    this.dependentTo.add(dependentTo);
  }

  public void removeDependency(Application dependentTo) {
    this.dependentTo.remove(dependentTo);
  }

  public Tag getTag() {
    return tag;
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

  public java.lang.String getEncodedPath() {
    return encodedPath;
  }

  public void setEncodedPath(java.lang.String encodedPath) {
    this.encodedPath = encodedPath;
  }
}
