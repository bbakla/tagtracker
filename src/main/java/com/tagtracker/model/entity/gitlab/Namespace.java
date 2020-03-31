package com.tagtracker.model.entity.gitlab;

public class Namespace {

  private float id;
  private String name;
  private String path;
  private String kind;
  private String full_path;
  private String parent_id = null;
  private String avatar_url;
  private String web_url;

  // Getter Methods

  public float getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getKind() {
    return kind;
  }

  public String getFull_path() {
    return full_path;
  }

  public String getParent_id() {
    return parent_id;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public String getWeb_url() {
    return web_url;
  }

  // Setter Methods

  public void setId(float id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public void setFull_path(String full_path) {
    this.full_path = full_path;
  }

  public void setParent_id(String parent_id) {
    this.parent_id = parent_id;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public void setWeb_url(String web_url) {
    this.web_url = web_url;
  }
}
