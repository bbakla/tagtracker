
package com.tagtracker.model.entity.gitlab.pipelines;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "file_type",
    "size",
    "filename",
    "file_format"
})
public class Artifact {

  @JsonProperty("file_type")
  private String fileType;
  @JsonProperty("size")
  private Integer size;
  @JsonProperty("filename")
  private String filename;
  @JsonProperty("file_format")
  private Object fileFormat;

  @JsonProperty("file_type")
  public String getFileType() {
    return fileType;
  }

  @JsonProperty("file_type")
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(Integer size) {
    this.size = size;
  }

  @JsonProperty("filename")
  public String getFilename() {
    return filename;
  }

  @JsonProperty("filename")
  public void setFilename(String filename) {
    this.filename = filename;
  }

  @JsonProperty("file_format")
  public Object getFileFormat() {
    return fileFormat;
  }

  @JsonProperty("file_format")
  public void setFileFormat(Object fileFormat) {
    this.fileFormat = fileFormat;
  }

}
