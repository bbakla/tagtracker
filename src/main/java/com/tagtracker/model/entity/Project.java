package com.tagtracker.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.web.util.UriUtils;

@Entity
@Table
public class Project extends Auditable<String> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // @NaturalId
  @Column(name = "project_id", nullable = false, unique = true)
  private String projectId;

  @NotNull
  @Column(name = "application_name")
  private java.lang.String projectName;

  @Column(name = "encoded_path", nullable = true)
  private String encodedPath;

  @OneToMany(mappedBy = "project",
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Tag> tags = new HashSet<>();

  //	@EmbeddedId
  //	private ApplicationIdentifier identifier;


  public Project() {
  }

  public Project(String id, String projectName) {
    this.projectId = id;
    this.projectName = projectName;
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

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(java.lang.String serviceName) {
    this.projectName = serviceName;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(java.lang.String id) {
    this.projectId = id;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public void addTag(Tag tag) {
    tags.add(tag);
  }

  public void removeTag(String tagName) {
    tags = tags.stream().filter(t -> !(t.getTagName()).equals(tagName)).collect(Collectors.toSet());
  }

  public Tag findTag(String tagName) {
    return tags.stream().filter(t -> t.getTagName().equals(tagName)).findFirst().get();
  }

  public java.lang.String getEncodedPath() {
    return encodedPath;
  }

  public void setEncodedPath(java.lang.String encodedPath) {
    this.encodedPath = encodedPath;
  }
}
