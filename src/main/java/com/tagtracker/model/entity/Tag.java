package com.tagtracker.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table//(uniqueConstraints = @UniqueConstraint(columnNames = {"tagName"}))
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  //@Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "tagName")
  private String tagName;

  private String message;

  private String releaseMessage;

  private Date commitMessage;

  @CreatedDate
  private Date createdDate;

  /*@OneToOne
  @MapsId
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  @JsonBackReference*/
  @ManyToOne
  private Project project;

  @MapKeyEnumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @MapKeyColumn(name = "ENV")
  @CollectionTable(name = "DEPLOYED_ENV")
  @Column(name = "IS_DEPLOYED")
  private Map<Environment, Boolean> deployedEnvironments = new EnumMap<>(Environment.class);

  //@ManyToMany(fetch = FetchType.EAGER)
  //@JsonBackReference
  @OneToMany
  private Set<Tag> dependentToMe = new HashSet();

  /*  @ManyToMany(mappedBy = "dependentToMe", fetch = FetchType.EAGER)
    @JsonManagedReference
    */
  @OneToMany
  private Set<Tag> dependentOn = new HashSet();


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getReleaseMessage() {
    return releaseMessage;
  }

  public void setReleaseMessage(String releaseMessage) {
    this.releaseMessage = releaseMessage;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public Project getProject() {
    return project;
  }

  public String getProjectName() {
    return project.getProjectName();
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getProjectId() {
    return this.project.getProjectId();
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

  public Set<Tag> getDependentOnMe() {
    return dependentToMe;
  }

  public void setDependentToMe(Set<Tag> dependentToMe) {
    this.dependentToMe = dependentToMe;
  }

  public void addDependencyToMe(Tag dependentToMe) {
    this.dependentToMe.add(dependentToMe);
  }

  public void removeDependentTag(Tag dependentToMe) {
    this.dependentToMe.remove(dependentToMe);
  }

  public Set<Tag> getDependentOn() {
    return dependentOn;
  }

  public void setDependentOn(Set<Tag> dependentTo) {
    this.dependentOn = dependentTo;
  }

  public void addDependentOn(Tag dependency) {
    if (checkIfDependencyAddedToTheDependentService(dependency).get() == null) {
      dependency.addDependencyToMe(this);
    }

    this.dependentOn.add(dependency);
  }

  public void removeDependentOn(Tag dependency) {
    if (checkIfDependencyAddedToTheDependentService(dependency).get() == null) {
      dependency.removeDependentTag(this);
    }

    this.dependentOn.remove(dependency);
  }

  public Date getCommitMessage() {
    return commitMessage;
  }

  public void setCommitMessage(Date commitMessage) {
    this.commitMessage = commitMessage;
  }

  public Set<Tag> getDependentToMe() {
    return dependentToMe;
  }

  private Optional<Tag> checkIfDependencyAddedToTheDependentService(Tag dependency) {
    return dependency.getDependentOnMe().
        stream().
        filter(t -> t.getTagName().equals(this.getTagName())).
        findAny();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Tag)) {
      return false;
    }

    Tag tag = (Tag) obj;

    return //(this.getId().equals(tag.getId())) &&
        (this.getMessage().equals(tag.getMessage())) &&
            (this.getTagName().equals(tag.getTagName())) &&
            (this.getCreatedDate().equals(tag.getCreatedDate())) &&
            (this.getReleaseMessage().equals(tag.getReleaseMessage()));
  }
}
