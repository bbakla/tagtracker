package com.tagtracker.model.entity.tracker;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"tag_name", "remote_project_id"})})
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = "tag_name")
  private String tagName;

  private String message;

  @Column(columnDefinition = "LONGTEXT")
  private String releaseMessage;

  private Date commitMessage;

  @CreatedDate
  private Date createdDate;

  @JoinColumn(name = "remote_project_id", referencedColumnName = "remote_project_id")
  @ManyToOne
  private Project project;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @CollectionTable(name = "pipeline_jobs",
      joinColumns = {@JoinColumn(name = "pipeline_tag_name", referencedColumnName = "tag_name")}
  )
  //@MapKey(name = "stage")
  private Map<String, Jobs> stages = new HashMap<>();

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "tag_relations",
      joinColumns = {@JoinColumn(name = "dependent_on_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private Set<Tag> relatedTags = new HashSet();


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

  public Map<String, Jobs> getStages() {
    return stages;
  }

  public void setStages(Map<String, Jobs> deployedEnvironments) {
    this.stages = deployedEnvironments;
  }

  public void addJobToStage(Job job) {
    Jobs jobs = this.stages.get(job.getStage());
    if (jobs == null) {
      jobs = new Jobs();
      jobs.setStage(job.getStage());
      addNewStage(jobs);
    }

    jobs.addJob(job);
  }

  public void addJobToStage(Set<Job> jobSet) {
    String stage = jobSet.iterator().next().getStage();
    Jobs jobs = this.stages.get(stage);
    if (jobs == null) {
      jobs = new Jobs();
      jobs.setStage(stage);
      jobs.setJobs(jobSet);
      addNewStage(jobs);
    } else {

      jobs.getJobs().addAll(jobSet);
    }
  }


  public void addNewStage(Jobs jobs) {
    if (this.stages.containsKey(jobs.getStage())) {
      System.out.printf("%s is already in the stage list", jobs.getStage());
    } else {
      this.stages.put(jobs.getStage(), jobs);
    }
  }

  public void setRelatedTags(Set<Tag> dependentToMe) {
    this.relatedTags = dependentToMe;
  }

  public void addRelatedTag(Tag dependentOnMe) {

    this.relatedTags.add(dependentOnMe);
  }

  public void removeRelatedTag(Tag dependentToMe) {
    this.relatedTags.remove(dependentToMe);
  }

  public Set<Tag> getDependentOn() {
    return dependentOn;
  }

  public void setDependentOn(Set<Tag> relatedTags) {
    this.relatedTags = relatedTags;
  }

  public void addDependency(Tag dependency) {
    if (!isMeInMyRelatedListOfDependency(dependency)) {
      dependency.addRelatedTag(this);
    }

    this.dependentOn.add(dependency);
  }

  public void removeDependency(Tag dependency) {
    if (isMeInMyRelatedListOfDependency(dependency)) {
      dependency.removeRelatedTag(this);
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

  private boolean isMeInMyRelatedListOfDependency(Tag dependency) {
    return dependency.getRelatedTags().
        stream().
        filter(t -> t.getTagName().equals(this.getTagName())).
        findAny().isEmpty() ? false : true;
  }

  private boolean isTagInDependencyList(String dependentOnThatTagName) {
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
