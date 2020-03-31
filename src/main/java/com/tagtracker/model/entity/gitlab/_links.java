package com.tagtracker.model.entity.gitlab;

public class _links {

  private String self;
  private String issues;
  private String merge_requests;
  private String repo_branches;
  private String labels;
  private String events;
  private String members;

  // Getter Methods

  public String getSelf() {
    return self;
  }

  public String getIssues() {
    return issues;
  }

  public String getMerge_requests() {
    return merge_requests;
  }

  public String getRepo_branches() {
    return repo_branches;
  }

  public String getLabels() {
    return labels;
  }

  public String getEvents() {
    return events;
  }

  public String getMembers() {
    return members;
  }

  // Setter Methods

  public void setSelf(String self) {
    this.self = self;
  }

  public void setIssues(String issues) {
    this.issues = issues;
  }

  public void setMerge_requests(String merge_requests) {
    this.merge_requests = merge_requests;
  }

  public void setRepo_branches(String repo_branches) {
    this.repo_branches = repo_branches;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public void setEvents(String events) {
    this.events = events;
  }

  public void setMembers(String members) {
    this.members = members;
  }
}
