package com.tagtracker.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table//(uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "tagName"}))
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  //@Column(name = "id")
  private Long id;

  private String message;

  private String releaseMessage;

  @CreatedDate
  private Date createdDate;

  @OneToOne
  @MapsId
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  @JsonBackReference
  private Application application;

  @NotNull
  @Column(name = "tagName")
  private String tagName;


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

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public String getProjectId() {
    return this.application.getProjectId();
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
