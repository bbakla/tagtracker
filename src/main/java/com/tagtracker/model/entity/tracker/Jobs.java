package com.tagtracker.model.entity.tracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Jobs {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(insertable = false, updatable = false)
  private String stage;

  @Column
  private Integer stageOrder;

  @Column
  private String pipelineId;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "job_list", joinColumns = @JoinColumn(name = "jobs_id"))
  private Set<Job> jobs = new HashSet<>();


  public Set<Job> getJobs() {
    return jobs;
  }

  public void setJobs(Set<Job> jobs) {
    this.jobs = jobs;
  }

  public void addJob(Job job) {
    jobs.add(job);
  }

  public  void removeJob(String jobId) {
    jobs.removeIf(job -> job.getJobId().equals(jobId));
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(String pipelineId) {
    this.pipelineId = pipelineId;
  }

  public Integer getStageOrder() {
    return stageOrder;
  }

  public void setStageOrder(Integer stageOrder) {
    this.stageOrder = stageOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Jobs jobs1 = (Jobs) o;
    return Objects.equals(id, jobs1.id) &&
        Objects.equals(stage, jobs1.stage) &&
        Objects.equals(stageOrder, jobs1.stageOrder) &&
        Objects.equals(pipelineId, jobs1.pipelineId) &&
        Objects.equals(jobs, jobs1.jobs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, stage, stageOrder, pipelineId, jobs);
  }
}
