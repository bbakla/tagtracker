package com.tagtracker.model.resource;

import java.util.Date;
import java.util.Objects;

public class JobResource implements Comparable<JobResource> {

  private String jobId;
  private String status;
  private String name;
  private String stage;
  private Date createdAt;
  private Date startedAt;
  private String duration;
  private UserResource userResource;
  private String pipelineStatus;
  private String pipelineId;


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPipelineStatus() {
    return pipelineStatus;
  }

  public void setPipelineStatus(String pipelineStatus) {
    this.pipelineStatus = pipelineStatus;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public UserResource getUserResource() {
    return userResource;
  }

  public void setUserResource(UserResource userResource) {
    this.userResource = userResource;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public String getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(String pipelineId) {
    this.pipelineId = pipelineId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobResource that = (JobResource) o;
    return Objects.equals(stage, that.stage) &&
        Objects.equals(jobId, that.getJobId()) &&
        Objects.equals(pipelineStatus, that.pipelineStatus) &&
        Objects.equals(pipelineId, that.pipelineId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stage, jobId, pipelineStatus, pipelineId);
  }

  @Override
  public int compareTo(JobResource o) {
    return Integer.parseInt(this.jobId) - Integer.parseInt(o.getJobId());
  }

}
