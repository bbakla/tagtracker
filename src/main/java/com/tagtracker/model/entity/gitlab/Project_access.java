package com.tagtracker.model.entity.gitlab;

public class Project_access {

  private float access_level;
  private float notification_level;

  // Getter Methods

  public float getAccess_level() {
    return access_level;
  }

  public float getNotification_level() {
    return notification_level;
  }

  // Setter Methods

  public void setAccess_level(float access_level) {
    this.access_level = access_level;
  }

  public void setNotification_level(float notification_level) {
    this.notification_level = notification_level;
  }
}

