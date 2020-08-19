package com.tagtracker.model.entity.tracker;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Job {

  private String jobId;

  private String stage;

  private String name;

  private String pipelineStatus;

  private String status;

  @Embedded
  private User user;


  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getJobId() {
    return jobId;
  }

  public String getPipelineStatus() {
    return pipelineStatus;
  }

  public void setPipelineStatus(String pipelineStatus) {
    this.pipelineStatus = pipelineStatus;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
