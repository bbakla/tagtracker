package com.tagtracker.model.entity.gitlab;

public class Owner {

  private float id;
  private String name;
  private String username;
  private String state;
  private String avatar_url;
  private String web_url;

  // Getter Methods

  public float getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getState() {
    return state;
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

  public void setUsername(String username) {
    this.username = username;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public void setWeb_url(String web_url) {
    this.web_url = web_url;
  }
}
