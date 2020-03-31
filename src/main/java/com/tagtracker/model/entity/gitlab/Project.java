package com.tagtracker.model.entity.gitlab;

import java.util.ArrayList;

public class Project {

  private String id;
  private String description;
  private String name;
  private String name_with_namespace;
  private String path;
  private String path_with_namespace;
  private String created_at;
  private String default_branch;
  ArrayList<Object> tag_list = new ArrayList<Object>();
  private String ssh_url_to_repo;
  private String http_url_to_repo;
  private String web_url;
  private String readme_url = null;
  private String avatar_url = null;
  private float star_count;
  private float forks_count;
  private String last_activity_at;
  Namespace NamespaceObject;
  _links _linksObject;
  private boolean empty_repo;
  private boolean archived;
  private String visibility;
  Owner OwnerObject;
  private boolean resolve_outdated_diff_discussions;
  private boolean container_registry_enabled;
  private boolean issues_enabled;
  private boolean merge_requests_enabled;
  private boolean wiki_enabled;
  private boolean jobs_enabled;
  private boolean snippets_enabled;
  private String issues_access_level;
  private String repository_access_level;
  private String merge_requests_access_level;
  private String wiki_access_level;
  private String builds_access_level;
  private String snippets_access_level;
  private boolean shared_runners_enabled;
  private boolean lfs_enabled;
  private float creator_id;
  private String import_status;
  private String import_error = null;
  private float open_issues_count;
  private String runners_token;
  private float ci_default_git_depth;
  private boolean public_jobs;
  private String build_git_strategy;
  private float build_timeout;
  private String auto_cancel_pending_pipelines;
  private String build_coverage_regex = null;
  private String ci_config_path = null;
  ArrayList<Object> shared_with_groups = new ArrayList<Object>();
  private boolean only_allow_merge_if_pipeline_succeeds;
  private boolean request_access_enabled;
  private boolean only_allow_merge_if_all_discussions_are_resolved;
  private boolean remove_source_branch_after_merge;
  private boolean printing_merge_request_link_enabled;
  private String merge_method;
  private boolean auto_devops_enabled;
  private String auto_devops_deploy_strategy;
  Permissions PermissionsObject;

  // Getter Methods

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public String getName_with_namespace() {
    return name_with_namespace;
  }

  public String getPath() {
    return path;
  }

  public String getPath_with_namespace() {
    return path_with_namespace;
  }

  public String getCreated_at() {
    return created_at;
  }

  public String getDefault_branch() {
    return default_branch;
  }

  public String getSsh_url_to_repo() {
    return ssh_url_to_repo;
  }

  public String getHttp_url_to_repo() {
    return http_url_to_repo;
  }

  public String getWeb_url() {
    return web_url;
  }

  public String getReadme_url() {
    return readme_url;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public float getStar_count() {
    return star_count;
  }

  public float getForks_count() {
    return forks_count;
  }

  public String getLast_activity_at() {
    return last_activity_at;
  }

  public Namespace getNamespace() {
    return NamespaceObject;
  }

  public _links get_links() {
    return _linksObject;
  }

  public boolean getEmpty_repo() {
    return empty_repo;
  }

  public boolean getArchived() {
    return archived;
  }

  public String getVisibility() {
    return visibility;
  }

  public Owner getOwner() {
    return OwnerObject;
  }

  public boolean getResolve_outdated_diff_discussions() {
    return resolve_outdated_diff_discussions;
  }

  public boolean getContainer_registry_enabled() {
    return container_registry_enabled;
  }

  public boolean getIssues_enabled() {
    return issues_enabled;
  }

  public boolean getMerge_requests_enabled() {
    return merge_requests_enabled;
  }

  public boolean getWiki_enabled() {
    return wiki_enabled;
  }

  public boolean getJobs_enabled() {
    return jobs_enabled;
  }

  public boolean getSnippets_enabled() {
    return snippets_enabled;
  }

  public String getIssues_access_level() {
    return issues_access_level;
  }

  public String getRepository_access_level() {
    return repository_access_level;
  }

  public String getMerge_requests_access_level() {
    return merge_requests_access_level;
  }

  public String getWiki_access_level() {
    return wiki_access_level;
  }

  public String getBuilds_access_level() {
    return builds_access_level;
  }

  public String getSnippets_access_level() {
    return snippets_access_level;
  }

  public boolean getShared_runners_enabled() {
    return shared_runners_enabled;
  }

  public boolean getLfs_enabled() {
    return lfs_enabled;
  }

  public float getCreator_id() {
    return creator_id;
  }

  public String getImport_status() {
    return import_status;
  }

  public String getImport_error() {
    return import_error;
  }

  public float getOpen_issues_count() {
    return open_issues_count;
  }

  public String getRunners_token() {
    return runners_token;
  }

  public float getCi_default_git_depth() {
    return ci_default_git_depth;
  }

  public boolean getPublic_jobs() {
    return public_jobs;
  }

  public String getBuild_git_strategy() {
    return build_git_strategy;
  }

  public float getBuild_timeout() {
    return build_timeout;
  }

  public String getAuto_cancel_pending_pipelines() {
    return auto_cancel_pending_pipelines;
  }

  public String getBuild_coverage_regex() {
    return build_coverage_regex;
  }

  public String getCi_config_path() {
    return ci_config_path;
  }

  public boolean getOnly_allow_merge_if_pipeline_succeeds() {
    return only_allow_merge_if_pipeline_succeeds;
  }

  public boolean getRequest_access_enabled() {
    return request_access_enabled;
  }

  public boolean getOnly_allow_merge_if_all_discussions_are_resolved() {
    return only_allow_merge_if_all_discussions_are_resolved;
  }

  public boolean getRemove_source_branch_after_merge() {
    return remove_source_branch_after_merge;
  }

  public boolean getPrinting_merge_request_link_enabled() {
    return printing_merge_request_link_enabled;
  }

  public String getMerge_method() {
    return merge_method;
  }

  public boolean getAuto_devops_enabled() {
    return auto_devops_enabled;
  }

  public String getAuto_devops_deploy_strategy() {
    return auto_devops_deploy_strategy;
  }

  public Permissions getPermissions() {
    return PermissionsObject;
  }

  // Setter Methods

  public void setId(String id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setName_with_namespace(String name_with_namespace) {
    this.name_with_namespace = name_with_namespace;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPath_with_namespace(String path_with_namespace) {
    this.path_with_namespace = path_with_namespace;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public void setDefault_branch(String default_branch) {
    this.default_branch = default_branch;
  }

  public void setSsh_url_to_repo(String ssh_url_to_repo) {
    this.ssh_url_to_repo = ssh_url_to_repo;
  }

  public void setHttp_url_to_repo(String http_url_to_repo) {
    this.http_url_to_repo = http_url_to_repo;
  }

  public void setWeb_url(String web_url) {
    this.web_url = web_url;
  }

  public void setReadme_url(String readme_url) {
    this.readme_url = readme_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public void setStar_count(float star_count) {
    this.star_count = star_count;
  }

  public void setForks_count(float forks_count) {
    this.forks_count = forks_count;
  }

  public void setLast_activity_at(String last_activity_at) {
    this.last_activity_at = last_activity_at;
  }

  public void setNamespace(Namespace namespaceObject) {
    this.NamespaceObject = namespaceObject;
  }

  public void set_links(_links _linksObject) {
    this._linksObject = _linksObject;
  }

  public void setEmpty_repo(boolean empty_repo) {
    this.empty_repo = empty_repo;
  }

  public void setArchived(boolean archived) {
    this.archived = archived;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public void setOwner(Owner ownerObject) {
    this.OwnerObject = ownerObject;
  }

  public void setResolve_outdated_diff_discussions(boolean resolve_outdated_diff_discussions) {
    this.resolve_outdated_diff_discussions = resolve_outdated_diff_discussions;
  }

  public void setContainer_registry_enabled(boolean container_registry_enabled) {
    this.container_registry_enabled = container_registry_enabled;
  }

  public void setIssues_enabled(boolean issues_enabled) {
    this.issues_enabled = issues_enabled;
  }

  public void setMerge_requests_enabled(boolean merge_requests_enabled) {
    this.merge_requests_enabled = merge_requests_enabled;
  }

  public void setWiki_enabled(boolean wiki_enabled) {
    this.wiki_enabled = wiki_enabled;
  }

  public void setJobs_enabled(boolean jobs_enabled) {
    this.jobs_enabled = jobs_enabled;
  }

  public void setSnippets_enabled(boolean snippets_enabled) {
    this.snippets_enabled = snippets_enabled;
  }

  public void setIssues_access_level(String issues_access_level) {
    this.issues_access_level = issues_access_level;
  }

  public void setRepository_access_level(String repository_access_level) {
    this.repository_access_level = repository_access_level;
  }

  public void setMerge_requests_access_level(String merge_requests_access_level) {
    this.merge_requests_access_level = merge_requests_access_level;
  }

  public void setWiki_access_level(String wiki_access_level) {
    this.wiki_access_level = wiki_access_level;
  }

  public void setBuilds_access_level(String builds_access_level) {
    this.builds_access_level = builds_access_level;
  }

  public void setSnippets_access_level(String snippets_access_level) {
    this.snippets_access_level = snippets_access_level;
  }

  public void setShared_runners_enabled(boolean shared_runners_enabled) {
    this.shared_runners_enabled = shared_runners_enabled;
  }

  public void setLfs_enabled(boolean lfs_enabled) {
    this.lfs_enabled = lfs_enabled;
  }

  public void setCreator_id(float creator_id) {
    this.creator_id = creator_id;
  }

  public void setImport_status(String import_status) {
    this.import_status = import_status;
  }

  public void setImport_error(String import_error) {
    this.import_error = import_error;
  }

  public void setOpen_issues_count(float open_issues_count) {
    this.open_issues_count = open_issues_count;
  }

  public void setRunners_token(String runners_token) {
    this.runners_token = runners_token;
  }

  public void setCi_default_git_depth(float ci_default_git_depth) {
    this.ci_default_git_depth = ci_default_git_depth;
  }

  public void setPublic_jobs(boolean public_jobs) {
    this.public_jobs = public_jobs;
  }

  public void setBuild_git_strategy(String build_git_strategy) {
    this.build_git_strategy = build_git_strategy;
  }

  public void setBuild_timeout(float build_timeout) {
    this.build_timeout = build_timeout;
  }

  public void setAuto_cancel_pending_pipelines(String auto_cancel_pending_pipelines) {
    this.auto_cancel_pending_pipelines = auto_cancel_pending_pipelines;
  }

  public void setBuild_coverage_regex(String build_coverage_regex) {
    this.build_coverage_regex = build_coverage_regex;
  }

  public void setCi_config_path(String ci_config_path) {
    this.ci_config_path = ci_config_path;
  }

  public void setOnly_allow_merge_if_pipeline_succeeds(
      boolean only_allow_merge_if_pipeline_succeeds) {
    this.only_allow_merge_if_pipeline_succeeds = only_allow_merge_if_pipeline_succeeds;
  }

  public void setRequest_access_enabled(boolean request_access_enabled) {
    this.request_access_enabled = request_access_enabled;
  }

  public void setOnly_allow_merge_if_all_discussions_are_resolved(
      boolean only_allow_merge_if_all_discussions_are_resolved) {
    this.only_allow_merge_if_all_discussions_are_resolved = only_allow_merge_if_all_discussions_are_resolved;
  }

  public void setRemove_source_branch_after_merge(boolean remove_source_branch_after_merge) {
    this.remove_source_branch_after_merge = remove_source_branch_after_merge;
  }

  public void setPrinting_merge_request_link_enabled(boolean printing_merge_request_link_enabled) {
    this.printing_merge_request_link_enabled = printing_merge_request_link_enabled;
  }

  public void setMerge_method(String merge_method) {
    this.merge_method = merge_method;
  }

  public void setAuto_devops_enabled(boolean auto_devops_enabled) {
    this.auto_devops_enabled = auto_devops_enabled;
  }

  public void setAuto_devops_deploy_strategy(String auto_devops_deploy_strategy) {
    this.auto_devops_deploy_strategy = auto_devops_deploy_strategy;
  }

  public void setPermissions(Permissions permissionsObject) {
    this.PermissionsObject = permissionsObject;
  }
}



