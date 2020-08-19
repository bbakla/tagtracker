package com.tagtracker.model.entity.gitlab.tags;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Commit {

  private String id;
  private String short_id;
  private String created_at;

  @JsonIgnore
  @JsonProperty("parent_ids")
  ArrayList<Object> parentIds = new ArrayList<Object>();
  private String title;
  private String message;
  private String author_name;
  private String author_email;
  private String authored_date;
  private String committer_name;
  private String committer_email;
  private String committed_date;

  // Getter Methods

  public String getId() {
    return id;
  }

  public String getShort_id() {
    return short_id;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

  public String getAuthor_name() {
    return author_name;
  }

  public String getAuthor_email() {
    return author_email;
  }

  public String getAuthored_date() {
    return authored_date;
  }

  public String getCommitter_name() {
    return committer_name;
  }

  public String getCommitter_email() {
    return committer_email;
  }

  public String getCommitted_date() {
    return committed_date;
  }

  // Setter Methods

  public void setId(String id) {
    this.id = id;
  }

  public void setShort_id(String short_id) {
    this.short_id = short_id;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setAuthor_name(String author_name) {
    this.author_name = author_name;
  }

  public void setAuthor_email(String author_email) {
    this.author_email = author_email;
  }

  public void setAuthored_date(String authored_date) {
    this.authored_date = authored_date;
  }

  public void setCommitter_name(String committer_name) {
    this.committer_name = committer_name;
  }

  public void setCommitter_email(String committer_email) {
    this.committer_email = committer_email;
  }

  public void setCommitted_date(String committed_date) {
    this.committed_date = committed_date;
  }
}
