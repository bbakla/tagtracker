
package com.tagtracker.model.entity.gitlab.pipelines;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "description",
    "ip_address",
    "active",
    "is_shared",
    "name",
    "online",
    "status"
})
public class Runner {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("description")
  private String description;
  @JsonProperty("ip_address")
  private String ipAddress;
  @JsonProperty("active")
  private Boolean active;
  @JsonProperty("is_shared")
  private Boolean isShared;
  @JsonProperty("name")
  private Object name;
  @JsonProperty("online")
  private Boolean online;
  @JsonProperty("status")
  private String status;

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty("ip_address")
  public String getIpAddress() {
    return ipAddress;
  }

  @JsonProperty("ip_address")
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @JsonProperty("active")
  public Boolean getActive() {
    return active;
  }

  @JsonProperty("active")
  public void setActive(Boolean active) {
    this.active = active;
  }

  @JsonProperty("is_shared")
  public Boolean getIsShared() {
    return isShared;
  }

  @JsonProperty("is_shared")
  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  @JsonProperty("name")
  public Object getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(Object name) {
    this.name = name;
  }

  @JsonProperty("online")
  public Boolean getOnline() {
    return online;
  }

  @JsonProperty("online")
  public void setOnline(Boolean online) {
    this.online = online;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

}
