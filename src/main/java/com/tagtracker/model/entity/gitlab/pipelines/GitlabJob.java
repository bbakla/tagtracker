
package com.tagtracker.model.entity.gitlab.pipelines;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "stage",
    "name",
    "ref",
    "tag",
    "coverage",
    "allow_failure",
    "created_at",
    "started_at",
    "finished_at",
    "duration",
    "user",
    "commit",
    "pipeline",
    "web_url",
    "artifacts",
    "runner",
    "artifacts_expire_at",
    "artifacts_file"
})
public class GitlabJob {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("stage")
    private String stage;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("tag")
    private Boolean tag;
    @JsonProperty("coverage")
    private Object coverage;
    @JsonProperty("allow_failure")
    private Boolean allowFailure;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("started_at")
    private String startedAt;
    @JsonProperty("finished_at")
    private String finishedAt;
    @JsonProperty("duration")
    private Float duration;
    @JsonProperty("user")
    private User user;
    @JsonProperty("commit")
    private Commit commit;
    @JsonProperty("pipeline")
    private Pipeline pipeline;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("artifacts")
    private List<Artifact> artifacts = null;
    @JsonProperty("runner")
    private Runner runner;
    @JsonProperty("artifacts_expire_at")
    private String artifactsExpireAt;
    @JsonProperty("artifacts_file")
    private ArtifactsFile artifactsFile;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }

    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ref")
    public String getRef() {
        return ref;
    }

    @JsonProperty("ref")
    public void setRef(String ref) {
        this.ref = ref;
    }

    @JsonProperty("tag")
    public Boolean getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    @JsonProperty("coverage")
    public Object getCoverage() {
        return coverage;
    }

    @JsonProperty("coverage")
    public void setCoverage(Object coverage) {
        this.coverage = coverage;
    }

    @JsonProperty("allow_failure")
    public Boolean getAllowFailure() {
        return allowFailure;
    }

    @JsonProperty("allow_failure")
    public void setAllowFailure(Boolean allowFailure) {
        this.allowFailure = allowFailure;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("started_at")
    public String getStartedAt() {
        return startedAt;
    }

    @JsonProperty("started_at")
    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    @JsonProperty("finished_at")
    public String getFinishedAt() {
        return finishedAt;
    }

    @JsonProperty("finished_at")
    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    @JsonProperty("duration")
    public Float getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Float duration) {
        this.duration = duration;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("commit")
    public Commit getCommit() {
        return commit;
    }

    @JsonProperty("commit")
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    @JsonProperty("pipeline")
    public Pipeline getPipeline() {
        return pipeline;
    }

    @JsonProperty("pipeline")
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @JsonProperty("artifacts")
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    @JsonProperty("artifacts")
    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    @JsonProperty("runner")
    public Runner getRunner() {
        return runner;
    }

    @JsonProperty("runner")
    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    @JsonProperty("artifacts_expire_at")
    public String getArtifactsExpireAt() {
        return artifactsExpireAt;
    }

    @JsonProperty("artifacts_expire_at")
    public void setArtifactsExpireAt(String artifactsExpireAt) {
        this.artifactsExpireAt = artifactsExpireAt;
    }

    @JsonProperty("artifacts_file")
    public ArtifactsFile getArtifactsFile() {
        return artifactsFile;
    }

    @JsonProperty("artifacts_file")
    public void setArtifactsFile(ArtifactsFile artifactsFile) {
        this.artifactsFile = artifactsFile;
    }

}
