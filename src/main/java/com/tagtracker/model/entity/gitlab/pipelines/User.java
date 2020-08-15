
package com.tagtracker.model.entity.gitlab.pipelines;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "username",
    "state",
    "avatar_url",
    "web_url",
    "created_at",
    "bio",
    "bio_html",
    "location",
    "public_email",
    "skype",
    "linkedin",
    "twitter",
    "website_url",
    "organization",
    "job_title",
    "work_information"
})
public class User {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("username")
  private String username;
  @JsonProperty("state")
  private String state;
  @JsonProperty("avatar_url")
  private String avatarUrl;
  @JsonProperty("web_url")
  private String webUrl;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("bio")
  private String bio;
  @JsonProperty("bio_html")
  private String bioHtml;
  @JsonProperty("location")
  private String location;
  @JsonProperty("public_email")
  private String publicEmail;
  @JsonProperty("skype")
  private String skype;
  @JsonProperty("linkedin")
  private String linkedin;
  @JsonProperty("twitter")
  private String twitter;
  @JsonProperty("website_url")
  private String websiteUrl;
  @JsonProperty("organization")
  private String organization;
  @JsonProperty("job_title")
  private String jobTitle;
  @JsonProperty("work_information")
  private String workInformation;

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

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  @JsonProperty("username")
  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty("state")
  public String getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(String state) {
    this.state = state;
  }

  @JsonProperty("avatar_url")
  public String getAvatarUrl() {
    return avatarUrl;
  }

  @JsonProperty("avatar_url")
  public void setAvatarUrl(String avatarUrl) {
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

  @JsonProperty("created_at")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("created_at")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  @JsonProperty("bio")
  public String getBio() {
    return bio;
  }

  @JsonProperty("bio")
  public void setBio(String bio) {
    this.bio = bio;
  }

  @JsonProperty("bio_html")
  public String getBioHtml() {
    return bioHtml;
  }

  @JsonProperty("bio_html")
  public void setBioHtml(String bioHtml) {
    this.bioHtml = bioHtml;
  }

  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  @JsonProperty("location")
  public void setLocation(String location) {
    this.location = location;
  }

  @JsonProperty("public_email")
  public String getPublicEmail() {
    return publicEmail;
  }

  @JsonProperty("public_email")
  public void setPublicEmail(String publicEmail) {
    this.publicEmail = publicEmail;
  }

  @JsonProperty("skype")
  public String getSkype() {
    return skype;
  }

  @JsonProperty("skype")
  public void setSkype(String skype) {
    this.skype = skype;
  }

  @JsonProperty("linkedin")
  public String getLinkedin() {
    return linkedin;
  }

  @JsonProperty("linkedin")
  public void setLinkedin(String linkedin) {
    this.linkedin = linkedin;
  }

  @JsonProperty("twitter")
  public String getTwitter() {
    return twitter;
  }

  @JsonProperty("twitter")
  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  @JsonProperty("website_url")
  public String getWebsiteUrl() {
    return websiteUrl;
  }

  @JsonProperty("website_url")
  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }

  @JsonProperty("organization")
  public String getOrganization() {
    return organization;
  }

  @JsonProperty("organization")
  public void setOrganization(String organization) {
    this.organization = organization;
  }

  @JsonProperty("job_title")
  public String getJobTitle() {
    return jobTitle;
  }

  @JsonProperty("job_title")
  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  @JsonProperty("work_information")
  public String getWorkInformation() {
    return workInformation;
  }

  @JsonProperty("work_information")
  public void setWorkInformation(String workInformation) {
    this.workInformation = workInformation;
  }

}
