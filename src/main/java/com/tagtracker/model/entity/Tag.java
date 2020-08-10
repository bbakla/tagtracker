package com.tagtracker.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.persistence.JoinColumn;
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


  @JoinColumn(name = "remote_project_id", referencedColumnName = "remote_project_id")
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
  @OneToMany(fetch = FetchType.EAGER)
  private Set<Tag> relatedTags = new HashSet();

  /*  @ManyToMany(mappedBy = "dependentToMe", fetch = FetchType.EAGER)
    @JsonManagedReference
    */
  @OneToMany(fetch = FetchType.EAGER)
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

  public String getRemoteProjectId() {
    return this.project.getRemoteProjectId();
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

  public void setRelatedTags(Set<Tag> dependentToMe) {
    this.relatedTags = dependentToMe;
  }

  public void addDependentOnMe(Tag dependentOnMe) {

    this.relatedTags.add(dependentOnMe);
  }

  public void removeDependentOnMe(Tag dependentToMe) {
    this.relatedTags.remove(dependentToMe);
  }

  public Set<Tag> getDependentOn() {
    return dependentOn;
  }

  public void setDependentOn(Set<Tag> relatedTags) {
    this.relatedTags = relatedTags;
  }

  public void addDependency(Tag dependency) {
    if (!isAddedDependentOnMeList(dependency)) {
      dependency.addDependentOnMe(this);
    }

    this.dependentOn.add(dependency);
  }

  public void removeDependency(Tag dependency) {
    if (isAddedDependentOnMeList(dependency)) {
      dependency.removeDependentOnMe(this);
    }

    this.dependentOn.remove(dependency);
  }

  public Date getCommitMessage() {
    return commitMessage;
  }

  public void setCommitMessage(Date commitMessage) {
    this.commitMessage = commitMessage;
  }

  public Set<Tag> getRelatedTags() {
    return relatedTags;
  }

  private boolean isAddedDependentOnMeList(Tag dependency) {
    return dependency.getRelatedTags().
        stream().
        filter(t -> t.getTagName().equals(this.getTagName())).
        findAny().isEmpty() ? false : true;
  }

  private boolean isAddedDependentOnList(String dependentOnThatTagName) {
    return this.getDependentOn()
        .stream()
        .filter(t -> t.getTagName().equals(dependentOnThatTagName))
        .findAny().isEmpty() ? false : true;
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
