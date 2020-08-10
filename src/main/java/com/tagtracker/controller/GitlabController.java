package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.GITLAB_PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;
import static com.tagtracker.controller.Constants.PROJECT_PATH_BY_ID;
import static com.tagtracker.controller.Constants.PROJECT_TAG_BY_NAME;

import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.GitlabTag;
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

  @GetMapping(PROJECT_PATH_BY_ID)
  public ResponseEntity<GitlabProject> getProjectByIdentifier(@PathVariable String identifier)
      throws ProjectNotFoundException {
    GitlabProject project = gitlabService.getProjectFromGitlab(identifier);

    return ResponseEntity.ok(project);
  }

  @GetMapping(GITLAB_PROJECT_TAGS_BY_IDENTIFIER)
  public ResponseEntity<GitlabTag[]> getProjectTags(@PathVariable String identifier)
      throws ProjectNotFoundException {
    GitlabTag[] tags = gitlabService.getTagsOfARemoteRepository(identifier);

    return ResponseEntity.ok(tags);
  }

  @GetMapping(PROJECT_TAG_BY_NAME)
  public ResponseEntity<GitlabTag> getProjectTagById(
      @PathVariable String identifier, @PathVariable String tagName)
      throws ProjectNotFoundException, TagNotFoundException {
    GitlabTag tag = gitlabService.getTagOfARemoteRepository(identifier, tagName);

    return ResponseEntity.ok(tag);
  }

}
