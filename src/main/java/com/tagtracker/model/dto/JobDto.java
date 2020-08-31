package com.tagtracker.model.dto;

public class JobDto {

  private String stage;
  private String name;
  private JOB_OPERATION jobOperation;

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

  public JOB_OPERATION getJobOperation() {
    return jobOperation;
  }

  public void setJobOperation(JOB_OPERATION jobOperation) {
    this.jobOperation = jobOperation;
  }
}
