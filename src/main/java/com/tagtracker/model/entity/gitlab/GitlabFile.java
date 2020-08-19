
package com.tagtracker.model.entity.gitlab;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "file_name",
    "file_path",
    "size",
    "encoding",
    "content_sha256",
    "ref",
    "blob_id",
    "commit_id",
    "last_commit_id",
    "content"
})
public class GitlabFile {

  @JsonProperty("file_name")
  private String fileName;

  @JsonProperty("file_path")
  private String filePath;

  @JsonProperty("size")
  private Integer size;

  @JsonProperty("encoding")
  private String encoding;

  @JsonProperty("content_sha256")
  private String contentSha256;

  @JsonProperty("ref")
  private String ref;

  @JsonProperty("blob_id")
  private String blobId;

  @JsonProperty("commit_id")
  private String commitId;

  @JsonProperty("last_commit_id")
  private String lastCommitId;

  @JsonProperty("content")
  private String content;

  @JsonProperty("file_name")
  public String getFileName() {
    return fileName;
  }

  @JsonProperty("file_name")
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @JsonProperty("file_path")
  public String getFilePath() {
    return filePath;
  }

  @JsonProperty("file_path")
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(Integer size) {
    this.size = size;
  }

  @JsonProperty("encoding")
  public String getEncoding() {
    return encoding;
  }

  @JsonProperty("encoding")
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  @JsonProperty("content_sha256")
  public String getContentSha256() {
    return contentSha256;
  }

  @JsonProperty("content_sha256")
  public void setContentSha256(String contentSha256) {
    this.contentSha256 = contentSha256;
  }

  @JsonProperty("ref")
  public String getRef() {
    return ref;
  }

  @JsonProperty("ref")
  public void setRef(String ref) {
    this.ref = ref;
  }

  @JsonProperty("blob_id")
  public String getBlobId() {
    return blobId;
  }

  @JsonProperty("blob_id")
  public void setBlobId(String blobId) {
    this.blobId = blobId;
  }

  @JsonProperty("commit_id")
  public String getCommitId() {
    return commitId;
  }

  @JsonProperty("commit_id")
  public void setCommitId(String commitId) {
    this.commitId = commitId;
  }

  @JsonProperty("last_commit_id")
  public String getLastCommitId() {
    return lastCommitId;
  }

  @JsonProperty("last_commit_id")
  public void setLastCommitId(String lastCommitId) {
    this.lastCommitId = lastCommitId;
  }

  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  @JsonProperty("content")
  public void setContent(String content) {
    this.content = content;
  }
}
