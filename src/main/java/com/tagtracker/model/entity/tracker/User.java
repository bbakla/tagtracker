package com.tagtracker.model.entity.tracker;

import javax.persistence.Embeddable;

//@Entity
@Embeddable
public class User {

  //@Id
  //private Long id;

  private Long userId;
  private String fullName;
  private String userName;


 /* public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }*/

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String name) {
    this.fullName = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
