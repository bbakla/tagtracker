package com.tagtracker.model.resource;

import com.tagtracker.model.entity.tracker.Jobs;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JobsResource implements Comparable<JobsResource> {

  private String stage;
  private Integer stageIndex;
  private String pipelineStatus;
  private String pipelineId;
  Set<JobResource> jobResources = new HashSet<>();

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

  public Integer getStageIndex() {
    return stageIndex;
  }

  public void setStageIndex(Integer stageIndex) {
    this.stageIndex = stageIndex;
  }

  public String getPipelineStatus() {
    return pipelineStatus;
  }

  public void setPipelineStatus(String pipelineStatus) {
    this.pipelineStatus = pipelineStatus;
  }

  public Set<JobResource> getJobResources() {
    return jobResources;
  }

  public void setJobResources(Set<JobResource> jobResources) {
    this.jobResources = jobResources;
  }

  public void addJob(JobResource jobResource) {
    jobResources.add(jobResource);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobsResource that = (JobsResource) o;
    return Objects.equals(stage, that.stage) &&
        Objects.equals(stageIndex, that.stageIndex) &&
        Objects.equals(pipelineStatus, that.pipelineStatus) &&
        Objects.equals(pipelineId, that.pipelineId) &&
        Objects.equals(jobResources, that.jobResources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stage, stageIndex, pipelineStatus, pipelineId, jobResources);
  }

  @Override
  public int compareTo(JobsResource o) {
    return this.stageIndex - o.getStageIndex();
  }
}
