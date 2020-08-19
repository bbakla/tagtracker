
package com.tagtracker.model.entity.gitlab;

import com.tagtracker.model.entity.gitlab.tags.Links;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "description",
    "name",
    "name_with_namespace",
    "path",
    "path_with_namespace",
    "created_at",
    "default_branch",
    "tag_list",
    "ssh_url_to_repo",
    "http_url_to_repo",
    "web_url",
    "readme_url",
    "avatar_url",
    "forks_count",
    "star_count",
    "last_activity_at",
    "namespace",
    "_links",
    "empty_repo",
    "archived",
    "visibility",
    "owner",
    "resolve_outdated_diff_discussions",
    "container_registry_enabled",
    "container_expiration_policy",
    "issues_enabled",
    "merge_requests_enabled",
    "wiki_enabled",
    "jobs_enabled",
    "snippets_enabled",
    "service_desk_enabled",
    "service_desk_address",
    "can_create_merge_request_in",
    "issues_access_level",
    "repository_access_level",
    "merge_requests_access_level",
    "forking_access_level",
    "wiki_access_level",
    "builds_access_level",
    "snippets_access_level",
    "pages_access_level",
    "emails_disabled",
    "shared_runners_enabled",
    "lfs_enabled",
    "creator_id",
    "import_status",
    "import_error",
    "open_issues_count",
    "runners_token",
    "ci_default_git_depth",
    "public_jobs",
    "build_git_strategy",
    "build_timeout",
    "auto_cancel_pending_pipelines",
    "build_coverage_regex",
    "ci_config_path",
    "shared_with_groups",
    "only_allow_merge_if_pipeline_succeeds",
    "allow_merge_on_skipped_pipeline",
    "request_access_enabled",
    "only_allow_merge_if_all_discussions_are_resolved",
    "remove_source_branch_after_merge",
    "printing_merge_request_link_enabled",
    "merge_method",
    "suggestion_commit_message",
    "auto_devops_enabled",
    "auto_devops_deploy_strategy",
    "autoclose_referenced_issues",
    "permissions"
})
public class GitlabProject {

  @JsonProperty("id")
  private String id;
  @JsonProperty("description")
  private String description;
  @JsonProperty("name")
  private String name;
  @JsonProperty("name_with_namespace")
  private String nameWithNamespace;
  @JsonProperty("path")
  private String path;
  @JsonProperty("path_with_namespace")
  private String pathWithNamespace;
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("default_branch")
  private String defaultBranch;
  @JsonProperty("tag_list")
  private List<Object> tagList = new ArrayList<Object>();
  @JsonProperty("ssh_url_to_repo")
  private String sshUrlToRepo;
  @JsonProperty("http_url_to_repo")
  private String httpUrlToRepo;
  @JsonProperty("web_url")
  private String webUrl;
  @JsonProperty("readme_url")
  private Object readmeUrl;
  @JsonProperty("avatar_url")
  private Object avatarUrl;
  @JsonProperty("forks_count")
  private Integer forksCount;
  @JsonProperty("star_count")
  private Integer starCount;
  @JsonProperty("last_activity_at")
  private String lastActivityAt;
  @JsonProperty("namespace")
  private Namespace namespace;
  @JsonProperty("_links")
  private Links links;
  @JsonProperty("empty_repo")
  private Boolean emptyRepo;
  @JsonProperty("archived")
  private Boolean archived;
  @JsonProperty("visibility")
  private String visibility;
  @JsonProperty("owner")
  private Owner owner;
  @JsonProperty("resolve_outdated_diff_discussions")
  private Boolean resolveOutdatedDiffDiscussions;
  @JsonProperty("container_registry_enabled")
  private Boolean containerRegistryEnabled;
  @JsonProperty("container_expiration_policy")
  private ContainerExpirationPolicy containerExpirationPolicy;
  @JsonProperty("issues_enabled")
  private Boolean issuesEnabled;
  @JsonProperty("merge_requests_enabled")
  private Boolean mergeRequestsEnabled;
  @JsonProperty("wiki_enabled")
  private Boolean wikiEnabled;
  @JsonProperty("jobs_enabled")
  private Boolean jobsEnabled;
  @JsonProperty("snippets_enabled")
  private Boolean snippetsEnabled;
  @JsonProperty("service_desk_enabled")
  private Boolean serviceDeskEnabled;
  @JsonProperty("service_desk_address")
  private Object serviceDeskAddress;
  @JsonProperty("can_create_merge_request_in")
  private Boolean canCreateMergeRequestIn;
  @JsonProperty("issues_access_level")
  private String issuesAccessLevel;
  @JsonProperty("repository_access_level")
  private String repositoryAccessLevel;
  @JsonProperty("merge_requests_access_level")
  private String mergeRequestsAccessLevel;
  @JsonProperty("forking_access_level")
  private String forkingAccessLevel;
  @JsonProperty("wiki_access_level")
  private String wikiAccessLevel;
  @JsonProperty("builds_access_level")
  private String buildsAccessLevel;
  @JsonProperty("snippets_access_level")
  private String snippetsAccessLevel;
  @JsonProperty("pages_access_level")
  private String pagesAccessLevel;
  @JsonProperty("emails_disabled")
  private Object emailsDisabled;
  @JsonProperty("shared_runners_enabled")
  private Boolean sharedRunnersEnabled;
  @JsonProperty("lfs_enabled")
  private Boolean lfsEnabled;
  @JsonProperty("creator_id")
  private Integer creatorId;
  @JsonProperty("import_status")
  private String importStatus;
  @JsonProperty("import_error")
  private Object importError;
  @JsonProperty("open_issues_count")
  private Integer openIssuesCount;
  @JsonProperty("runners_token")
  private String runnersToken;
  @JsonProperty("ci_default_git_depth")
  private Integer ciDefaultGitDepth;
  @JsonProperty("public_jobs")
  private Boolean publicJobs;
  @JsonProperty("build_git_strategy")
  private String buildGitStrategy;
  @JsonProperty("build_timeout")
  private Integer buildTimeout;
  @JsonProperty("auto_cancel_pending_pipelines")
  private String autoCancelPendingPipelines;
  @JsonProperty("build_coverage_regex")
  private Object buildCoverageRegex;
  @JsonProperty("ci_config_path")
  private Object ciConfigPath;
  @JsonProperty("shared_with_groups")
  private List<Object> sharedWithGroups = new ArrayList<Object>();
  @JsonProperty("only_allow_merge_if_pipeline_succeeds")
  private Boolean onlyAllowMergeIfPipelineSucceeds;
  @JsonProperty("allow_merge_on_skipped_pipeline")
  private Object allowMergeOnSkippedPipeline;
  @JsonProperty("request_access_enabled")
  private Boolean requestAccessEnabled;
  @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
  private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
  @JsonProperty("remove_source_branch_after_merge")
  private Boolean removeSourceBranchAfterMerge;
  @JsonProperty("printing_merge_request_link_enabled")
  private Boolean printingMergeRequestLinkEnabled;
  @JsonProperty("merge_method")
  private String mergeMethod;
  @JsonProperty("suggestion_commit_message")
  private Object suggestionCommitMessage;
  @JsonProperty("auto_devops_enabled")
  private Boolean autoDevopsEnabled;
  @JsonProperty("auto_devops_deploy_strategy")
  private String autoDevopsDeployStrategy;
  @JsonProperty("autoclose_referenced_issues")
  private Boolean autocloseReferencedIssues;
  @JsonProperty("permissions")
  private Permissions permissions;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
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

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("name_with_namespace")
  public String getNameWithNamespace() {
    return nameWithNamespace;
  }

  @JsonProperty("name_with_namespace")
  public void setNameWithNamespace(String nameWithNamespace) {
    this.nameWithNamespace = nameWithNamespace;
  }

  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  @JsonProperty("path")
  public void setPath(String path) {
    this.path = path;
  }

  @JsonProperty("path_with_namespace")
  public String getPathWithNamespace() {
    return pathWithNamespace;
  }

  @JsonProperty("path_with_namespace")
  public void setPathWithNamespace(String pathWithNamespace) {
    this.pathWithNamespace = pathWithNamespace;
  }

  @JsonProperty("created_at")
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("created_at")
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  @JsonProperty("default_branch")
  public String getDefaultBranch() {
    return defaultBranch;
  }

  @JsonProperty("default_branch")
  public void setDefaultBranch(String defaultBranch) {
    this.defaultBranch = defaultBranch;
  }

  @JsonProperty("tag_list")
  public List<Object> getTagList() {
    return tagList;
  }

  @JsonProperty("tag_list")
  public void setTagList(List<Object> tagList) {
    this.tagList = tagList;
  }

  @JsonProperty("ssh_url_to_repo")
  public String getSshUrlToRepo() {
    return sshUrlToRepo;
  }

  @JsonProperty("ssh_url_to_repo")
  public void setSshUrlToRepo(String sshUrlToRepo) {
    this.sshUrlToRepo = sshUrlToRepo;
  }

  @JsonProperty("http_url_to_repo")
  public String getHttpUrlToRepo() {
    return httpUrlToRepo;
  }

  @JsonProperty("http_url_to_repo")
  public void setHttpUrlToRepo(String httpUrlToRepo) {
    this.httpUrlToRepo = httpUrlToRepo;
  }

  @JsonProperty("web_url")
  public String getWebUrl() {
    return webUrl;
  }

  @JsonProperty("web_url")
  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  @JsonProperty("readme_url")
  public Object getReadmeUrl() {
    return readmeUrl;
  }

  @JsonProperty("readme_url")
  public void setReadmeUrl(Object readmeUrl) {
    this.readmeUrl = readmeUrl;
  }

  @JsonProperty("avatar_url")
  public Object getAvatarUrl() {
    return avatarUrl;
  }

  @JsonProperty("avatar_url")
  public void setAvatarUrl(Object avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @JsonProperty("forks_count")
  public Integer getForksCount() {
    return forksCount;
  }

  @JsonProperty("forks_count")
  public void setForksCount(Integer forksCount) {
    this.forksCount = forksCount;
  }

  @JsonProperty("star_count")
  public Integer getStarCount() {
    return starCount;
  }

  @JsonProperty("star_count")
  public void setStarCount(Integer starCount) {
    this.starCount = starCount;
  }

  @JsonProperty("last_activity_at")
  public String getLastActivityAt() {
    return lastActivityAt;
  }

  @JsonProperty("last_activity_at")
  public void setLastActivityAt(String lastActivityAt) {
    this.lastActivityAt = lastActivityAt;
  }

  @JsonProperty("namespace")
  public Namespace getNamespace() {
    return namespace;
  }

  @JsonProperty("namespace")
  public void setNamespace(Namespace namespace) {
    this.namespace = namespace;
  }

  @JsonProperty("_links")
  public Links getLinks() {
    return links;
  }

  @JsonProperty("_links")
  public void setLinks(Links links) {
    this.links = links;
  }

  @JsonProperty("empty_repo")
  public Boolean getEmptyRepo() {
    return emptyRepo;
  }

  @JsonProperty("empty_repo")
  public void setEmptyRepo(Boolean emptyRepo) {
    this.emptyRepo = emptyRepo;
  }

  @JsonProperty("archived")
  public Boolean getArchived() {
    return archived;
  }

  @JsonProperty("archived")
  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  @JsonProperty("visibility")
  public String getVisibility() {
    return visibility;
  }

  @JsonProperty("visibility")
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  @JsonProperty("owner")
  public Owner getOwner() {
    return owner;
  }

  @JsonProperty("owner")
  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  @JsonProperty("resolve_outdated_diff_discussions")
  public Boolean getResolveOutdatedDiffDiscussions() {
    return resolveOutdatedDiffDiscussions;
  }

  @JsonProperty("resolve_outdated_diff_discussions")
  public void setResolveOutdatedDiffDiscussions(Boolean resolveOutdatedDiffDiscussions) {
    this.resolveOutdatedDiffDiscussions = resolveOutdatedDiffDiscussions;
  }

  @JsonProperty("container_registry_enabled")
  public Boolean getContainerRegistryEnabled() {
    return containerRegistryEnabled;
  }

  @JsonProperty("container_registry_enabled")
  public void setContainerRegistryEnabled(Boolean containerRegistryEnabled) {
    this.containerRegistryEnabled = containerRegistryEnabled;
  }

  @JsonProperty("container_expiration_policy")
  public ContainerExpirationPolicy getContainerExpirationPolicy() {
    return containerExpirationPolicy;
  }

  @JsonProperty("container_expiration_policy")
  public void setContainerExpirationPolicy(ContainerExpirationPolicy containerExpirationPolicy) {
    this.containerExpirationPolicy = containerExpirationPolicy;
  }

  @JsonProperty("issues_enabled")
  public Boolean getIssuesEnabled() {
    return issuesEnabled;
  }

  @JsonProperty("issues_enabled")
  public void setIssuesEnabled(Boolean issuesEnabled) {
    this.issuesEnabled = issuesEnabled;
  }

  @JsonProperty("merge_requests_enabled")
  public Boolean getMergeRequestsEnabled() {
    return mergeRequestsEnabled;
  }

  @JsonProperty("merge_requests_enabled")
  public void setMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
    this.mergeRequestsEnabled = mergeRequestsEnabled;
  }

  @JsonProperty("wiki_enabled")
  public Boolean getWikiEnabled() {
    return wikiEnabled;
  }

  @JsonProperty("wiki_enabled")
  public void setWikiEnabled(Boolean wikiEnabled) {
    this.wikiEnabled = wikiEnabled;
  }

  @JsonProperty("jobs_enabled")
  public Boolean getJobsEnabled() {
    return jobsEnabled;
  }

  @JsonProperty("jobs_enabled")
  public void setJobsEnabled(Boolean jobsEnabled) {
    this.jobsEnabled = jobsEnabled;
  }

  @JsonProperty("snippets_enabled")
  public Boolean getSnippetsEnabled() {
    return snippetsEnabled;
  }

  @JsonProperty("snippets_enabled")
  public void setSnippetsEnabled(Boolean snippetsEnabled) {
    this.snippetsEnabled = snippetsEnabled;
  }

  @JsonProperty("service_desk_enabled")
  public Boolean getServiceDeskEnabled() {
    return serviceDeskEnabled;
  }

  @JsonProperty("service_desk_enabled")
  public void setServiceDeskEnabled(Boolean serviceDeskEnabled) {
    this.serviceDeskEnabled = serviceDeskEnabled;
  }

  @JsonProperty("service_desk_address")
  public Object getServiceDeskAddress() {
    return serviceDeskAddress;
  }

  @JsonProperty("service_desk_address")
  public void setServiceDeskAddress(Object serviceDeskAddress) {
    this.serviceDeskAddress = serviceDeskAddress;
  }

  @JsonProperty("can_create_merge_request_in")
  public Boolean getCanCreateMergeRequestIn() {
    return canCreateMergeRequestIn;
  }

  @JsonProperty("can_create_merge_request_in")
  public void setCanCreateMergeRequestIn(Boolean canCreateMergeRequestIn) {
    this.canCreateMergeRequestIn = canCreateMergeRequestIn;
  }

  @JsonProperty("issues_access_level")
  public String getIssuesAccessLevel() {
    return issuesAccessLevel;
  }

  @JsonProperty("issues_access_level")
  public void setIssuesAccessLevel(String issuesAccessLevel) {
    this.issuesAccessLevel = issuesAccessLevel;
  }

  @JsonProperty("repository_access_level")
  public String getRepositoryAccessLevel() {
    return repositoryAccessLevel;
  }

  @JsonProperty("repository_access_level")
  public void setRepositoryAccessLevel(String repositoryAccessLevel) {
    this.repositoryAccessLevel = repositoryAccessLevel;
  }

  @JsonProperty("merge_requests_access_level")
  public String getMergeRequestsAccessLevel() {
    return mergeRequestsAccessLevel;
  }

  @JsonProperty("merge_requests_access_level")
  public void setMergeRequestsAccessLevel(String mergeRequestsAccessLevel) {
    this.mergeRequestsAccessLevel = mergeRequestsAccessLevel;
  }

  @JsonProperty("forking_access_level")
  public String getForkingAccessLevel() {
    return forkingAccessLevel;
  }

  @JsonProperty("forking_access_level")
  public void setForkingAccessLevel(String forkingAccessLevel) {
    this.forkingAccessLevel = forkingAccessLevel;
  }

  @JsonProperty("wiki_access_level")
  public String getWikiAccessLevel() {
    return wikiAccessLevel;
  }

  @JsonProperty("wiki_access_level")
  public void setWikiAccessLevel(String wikiAccessLevel) {
    this.wikiAccessLevel = wikiAccessLevel;
  }

  @JsonProperty("builds_access_level")
  public String getBuildsAccessLevel() {
    return buildsAccessLevel;
  }

  @JsonProperty("builds_access_level")
  public void setBuildsAccessLevel(String buildsAccessLevel) {
    this.buildsAccessLevel = buildsAccessLevel;
  }

  @JsonProperty("snippets_access_level")
  public String getSnippetsAccessLevel() {
    return snippetsAccessLevel;
  }

  @JsonProperty("snippets_access_level")
  public void setSnippetsAccessLevel(String snippetsAccessLevel) {
    this.snippetsAccessLevel = snippetsAccessLevel;
  }

  @JsonProperty("pages_access_level")
  public String getPagesAccessLevel() {
    return pagesAccessLevel;
  }

  @JsonProperty("pages_access_level")
  public void setPagesAccessLevel(String pagesAccessLevel) {
    this.pagesAccessLevel = pagesAccessLevel;
  }

  @JsonProperty("emails_disabled")
  public Object getEmailsDisabled() {
    return emailsDisabled;
  }

  @JsonProperty("emails_disabled")
  public void setEmailsDisabled(Object emailsDisabled) {
    this.emailsDisabled = emailsDisabled;
  }

  @JsonProperty("shared_runners_enabled")
  public Boolean getSharedRunnersEnabled() {
    return sharedRunnersEnabled;
  }

  @JsonProperty("shared_runners_enabled")
  public void setSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
    this.sharedRunnersEnabled = sharedRunnersEnabled;
  }

  @JsonProperty("lfs_enabled")
  public Boolean getLfsEnabled() {
    return lfsEnabled;
  }

  @JsonProperty("lfs_enabled")
  public void setLfsEnabled(Boolean lfsEnabled) {
    this.lfsEnabled = lfsEnabled;
  }

  @JsonProperty("creator_id")
  public Integer getCreatorId() {
    return creatorId;
  }

  @JsonProperty("creator_id")
  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  @JsonProperty("import_status")
  public String getImportStatus() {
    return importStatus;
  }

  @JsonProperty("import_status")
  public void setImportStatus(String importStatus) {
    this.importStatus = importStatus;
  }

  @JsonProperty("import_error")
  public Object getImportError() {
    return importError;
  }

  @JsonProperty("import_error")
  public void setImportError(Object importError) {
    this.importError = importError;
  }

  @JsonProperty("open_issues_count")
  public Integer getOpenIssuesCount() {
    return openIssuesCount;
  }

  @JsonProperty("open_issues_count")
  public void setOpenIssuesCount(Integer openIssuesCount) {
    this.openIssuesCount = openIssuesCount;
  }

  @JsonProperty("runners_token")
  public String getRunnersToken() {
    return runnersToken;
  }

  @JsonProperty("runners_token")
  public void setRunnersToken(String runnersToken) {
    this.runnersToken = runnersToken;
  }

  @JsonProperty("ci_default_git_depth")
  public Integer getCiDefaultGitDepth() {
    return ciDefaultGitDepth;
  }

  @JsonProperty("ci_default_git_depth")
  public void setCiDefaultGitDepth(Integer ciDefaultGitDepth) {
    this.ciDefaultGitDepth = ciDefaultGitDepth;
  }

  @JsonProperty("public_jobs")
  public Boolean getPublicJobs() {
    return publicJobs;
  }

  @JsonProperty("public_jobs")
  public void setPublicJobs(Boolean publicJobs) {
    this.publicJobs = publicJobs;
  }

  @JsonProperty("build_git_strategy")
  public String getBuildGitStrategy() {
    return buildGitStrategy;
  }

  @JsonProperty("build_git_strategy")
  public void setBuildGitStrategy(String buildGitStrategy) {
    this.buildGitStrategy = buildGitStrategy;
  }

  @JsonProperty("build_timeout")
  public Integer getBuildTimeout() {
    return buildTimeout;
  }

  @JsonProperty("build_timeout")
  public void setBuildTimeout(Integer buildTimeout) {
    this.buildTimeout = buildTimeout;
  }

  @JsonProperty("auto_cancel_pending_pipelines")
  public String getAutoCancelPendingPipelines() {
    return autoCancelPendingPipelines;
  }

  @JsonProperty("auto_cancel_pending_pipelines")
  public void setAutoCancelPendingPipelines(String autoCancelPendingPipelines) {
    this.autoCancelPendingPipelines = autoCancelPendingPipelines;
  }

  @JsonProperty("build_coverage_regex")
  public Object getBuildCoverageRegex() {
    return buildCoverageRegex;
  }

  @JsonProperty("build_coverage_regex")
  public void setBuildCoverageRegex(Object buildCoverageRegex) {
    this.buildCoverageRegex = buildCoverageRegex;
  }

  @JsonProperty("ci_config_path")
  public Object getCiConfigPath() {
    return ciConfigPath;
  }

  @JsonProperty("ci_config_path")
  public void setCiConfigPath(Object ciConfigPath) {
    this.ciConfigPath = ciConfigPath;
  }

  @JsonProperty("shared_with_groups")
  public List<Object> getSharedWithGroups() {
    return sharedWithGroups;
  }

  @JsonProperty("shared_with_groups")
  public void setSharedWithGroups(List<Object> sharedWithGroups) {
    this.sharedWithGroups = sharedWithGroups;
  }

  @JsonProperty("only_allow_merge_if_pipeline_succeeds")
  public Boolean getOnlyAllowMergeIfPipelineSucceeds() {
    return onlyAllowMergeIfPipelineSucceeds;
  }

  @JsonProperty("only_allow_merge_if_pipeline_succeeds")
  public void setOnlyAllowMergeIfPipelineSucceeds(Boolean onlyAllowMergeIfPipelineSucceeds) {
    this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
  }

  @JsonProperty("allow_merge_on_skipped_pipeline")
  public Object getAllowMergeOnSkippedPipeline() {
    return allowMergeOnSkippedPipeline;
  }

  @JsonProperty("allow_merge_on_skipped_pipeline")
  public void setAllowMergeOnSkippedPipeline(Object allowMergeOnSkippedPipeline) {
    this.allowMergeOnSkippedPipeline = allowMergeOnSkippedPipeline;
  }

  @JsonProperty("request_access_enabled")
  public Boolean getRequestAccessEnabled() {
    return requestAccessEnabled;
  }

  @JsonProperty("request_access_enabled")
  public void setRequestAccessEnabled(Boolean requestAccessEnabled) {
    this.requestAccessEnabled = requestAccessEnabled;
  }

  @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
  public Boolean getOnlyAllowMergeIfAllDiscussionsAreResolved() {
    return onlyAllowMergeIfAllDiscussionsAreResolved;
  }

  @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
  public void setOnlyAllowMergeIfAllDiscussionsAreResolved(
      Boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
    this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
  }

  @JsonProperty("remove_source_branch_after_merge")
  public Boolean getRemoveSourceBranchAfterMerge() {
    return removeSourceBranchAfterMerge;
  }

  @JsonProperty("remove_source_branch_after_merge")
  public void setRemoveSourceBranchAfterMerge(Boolean removeSourceBranchAfterMerge) {
    this.removeSourceBranchAfterMerge = removeSourceBranchAfterMerge;
  }

  @JsonProperty("printing_merge_request_link_enabled")
  public Boolean getPrintingMergeRequestLinkEnabled() {
    return printingMergeRequestLinkEnabled;
  }

  @JsonProperty("printing_merge_request_link_enabled")
  public void setPrintingMergeRequestLinkEnabled(Boolean printingMergeRequestLinkEnabled) {
    this.printingMergeRequestLinkEnabled = printingMergeRequestLinkEnabled;
  }

  @JsonProperty("merge_method")
  public String getMergeMethod() {
    return mergeMethod;
  }

  @JsonProperty("merge_method")
  public void setMergeMethod(String mergeMethod) {
    this.mergeMethod = mergeMethod;
  }

  @JsonProperty("suggestion_commit_message")
  public Object getSuggestionCommitMessage() {
    return suggestionCommitMessage;
  }

  @JsonProperty("suggestion_commit_message")
  public void setSuggestionCommitMessage(Object suggestionCommitMessage) {
    this.suggestionCommitMessage = suggestionCommitMessage;
  }

  @JsonProperty("auto_devops_enabled")
  public Boolean getAutoDevopsEnabled() {
    return autoDevopsEnabled;
  }

  @JsonProperty("auto_devops_enabled")
  public void setAutoDevopsEnabled(Boolean autoDevopsEnabled) {
    this.autoDevopsEnabled = autoDevopsEnabled;
  }

  @JsonProperty("auto_devops_deploy_strategy")
  public String getAutoDevopsDeployStrategy() {
    return autoDevopsDeployStrategy;
  }

  @JsonProperty("auto_devops_deploy_strategy")
  public void setAutoDevopsDeployStrategy(String autoDevopsDeployStrategy) {
    this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
  }

  @JsonProperty("autoclose_referenced_issues")
  public Boolean getAutocloseReferencedIssues() {
    return autocloseReferencedIssues;
  }

  @JsonProperty("autoclose_referenced_issues")
  public void setAutocloseReferencedIssues(Boolean autocloseReferencedIssues) {
    this.autocloseReferencedIssues = autocloseReferencedIssues;
  }

  @JsonProperty("permissions")
  public Permissions getPermissions() {
    return permissions;
  }

  @JsonProperty("permissions")
  public void setPermissions(Permissions permissions) {
    this.permissions = permissions;
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