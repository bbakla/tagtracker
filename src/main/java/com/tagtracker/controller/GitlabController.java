package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.GITLAB_PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_BY_IDENTIFIER;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_SPECIFIC_TAGS_BY_IDENTIFIER;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;

import com.tagtracker.model.entity.gitlab.Project;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import com.tagtracker.service.ProjectNotFoundException;
import com.tagtracker.service.gitlab.GitlabService;
import com.tagtracker.service.gitlab.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(GITLAB_PROJECT_BASE_PATH)
public class GitlabController {

  @Autowired
  private GitlabService gitlabService;

  @GetMapping(GITLAB_PROJECT_BY_IDENTIFIER)
  public ResponseEntity<Project> getProjectByIdentifier(@PathVariable String identifier)
      throws ProjectNotFoundException {
    Project project = gitlabService.getProjectFromGitlab(identifier);

    return ResponseEntity.ok(project);
  }

  @GetMapping(GITLAB_PROJECT_TAGS_BY_IDENTIFIER)
  public ResponseEntity<RemoteTag[]> getProjectTags(@PathVariable String identifier)
      throws ProjectNotFoundException {
    RemoteTag[] tags = gitlabService.getTagsOfProject(identifier);

    return ResponseEntity.ok(tags);
  }

  @GetMapping(GITLAB_PROJECT_SPECIFIC_TAGS_BY_IDENTIFIER)
  public ResponseEntity<RemoteTag> getProjectTagById(
      @PathVariable String identifier, @PathVariable String tagName)
      throws ProjectNotFoundException, TagNotFoundException {
    RemoteTag tag = gitlabService.getTagOfARepo(identifier, tagName);

    return ResponseEntity.ok(tag);
  }

}
