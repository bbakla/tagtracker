
package com.tagtracker.model.entity.gitlab.pipelines;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "sha",
    "ref",
    "status",
    "created_at",
    "updated_at",
    "web_url"
})
public class Pipeline {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("sha")
  private String sha;
  @JsonProperty("ref")
  private String ref;
  @JsonProperty("status")
  private String status;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("updated_at")
  private String updatedAt;
  @JsonProperty("web_url")
  private String webUrl;

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("sha")
  public String getSha() {
    return sha;
  }

  @JsonProperty("sha")
  public void setSha(String sha) {
    this.sha = sha;
  }

  @JsonProperty("ref")
  public String getRef() {
    return ref;
  }

  @JsonProperty("ref")
  public void setRef(String ref) {
    this.ref = ref;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  @JsonProperty("created_at")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("created_at")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  @JsonProperty("updated_at")
  public String getUpdatedAt() {
    return updatedAt;
  }

  @JsonProperty("updated_at")
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @JsonProperty("web_url")
  public String getWebUrl() {
    return webUrl;
  }

  @JsonProperty("web_url")
  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

}
