
package com.tagtracker.model.entity.gitlab;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "path",
    "kind",
    "full_path",
    "parent_id",
    "avatar_url",
    "web_url"
})
public class Namespace {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("path")
  private String path;
  @JsonProperty("kind")
  private String kind;
  @JsonProperty("full_path")
  private String fullPath;
  @JsonProperty("parent_id")
  private Integer parentId;
  @JsonProperty("avatar_url")
  private Object avatarUrl;
  @JsonProperty("web_url")
  private String webUrl;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  @JsonProperty("path")
  public void setPath(String path) {
    this.path = path;
  }

  @JsonProperty("kind")
  public String getKind() {
    return kind;
  }

  @JsonProperty("kind")
  public void setKind(String kind) {
    this.kind = kind;
  }

  @JsonProperty("full_path")
  public String getFullPath() {
    return fullPath;
  }

  @JsonProperty("full_path")
  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  @JsonProperty("parent_id")
  public Integer getParentId() {
    return parentId;
  }

  @JsonProperty("parent_id")
  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  @JsonProperty("avatar_url")
  public Object getAvatarUrl() {
    return avatarUrl;
  }

  @JsonProperty("avatar_url")
  public void setAvatarUrl(Object avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @JsonProperty("web_url")
  public String getWebUrl() {
    return webUrl;
  }

  @JsonProperty("web_url")
  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
