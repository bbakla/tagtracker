package com.tagtracker.model.resource;

public class StageSequenceResource {

  private int order;
  private String stage;

  public StageSequenceResource(int order, String stage) {
    this.order = order;
    this.stage = stage;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }
}
