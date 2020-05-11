package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.PROJECT_PATH_BY_ID;
import static com.tagtracker.controller.Constants.PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH;
import static com.tagtracker.controller.Constants.PROJECT_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.Constants.PROJECT_PATH_TO_DEPLOY;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;

import com.tagtracker.model.dto.ProjectDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.service.ProjectService;
import com.tagtracker.service.ProjectNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PROJECT_BASE_PATH)
public class ProjectRepositoryController {

  @Autowired
  private ProjectService projectService;

  @GetMapping(PROJECT_PATH_BY_ID)
  public ResponseEntity<ProjectResource> getProject(@PathVariable String identifier)
      throws ProjectNotFoundException {
    ProjectResource project = projectService.getByProjectIdOrPath(identifier);

    return ResponseEntity.ok().body(project);
  }

  @PostMapping()
  public ResponseEntity<ProjectResource> saveApplication(
      @Valid @RequestBody ProjectDto projectDto)
      throws ProjectNotFoundException, URISyntaxException {

    ProjectResource project =
        projectService.saveProject(projectDto.getIdentifier());

    return ResponseEntity.created(new URI(PROJECT_BASE_PATH + "/" + project.getProjectId()))
        .body(project);
  }

  @PatchMapping(PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH)
  public ResponseEntity<ProjectResource> addDependency(
      @PathVariable String identifier, @PathVariable String tagName,
      @RequestBody DependencyDto dependentTo)

      throws ProjectNotFoundException {

    ProjectResource application = projectService
        .addDependency(identifier, tagName, dependentTo);

    return ResponseEntity.ok().body(application);
  }

  @PatchMapping(PROJECT_PATH_TO_DEPLOY)
  public ResponseEntity<ProjectResource> deployTo(
      @PathVariable String identifier, @PathVariable Environment environment)
      throws ProjectNotFoundException {
    ProjectResource projectResource = projectService
        .deploy(identifier, environment);

    return ResponseEntity.ok().body(projectResource);
  }

  @DeleteMapping(PROJECT_PATH_BY_ID)
  public ResponseEntity<?> deleteApplication(@PathVariable String identifier)
      throws ProjectNotFoundException {
    projectService.deleteApp(identifier);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(PROJECT_PATH_DELETE_TAG_BY_NAME)
  public ResponseEntity<?> deleteTag(
      @PathVariable String identifier,
      @PathVariable String tagName,
      @RequestParam(defaultValue = "true", required = true) Boolean deleteRemoteTag)
      throws ProjectNotFoundException {

    projectService.deleteTag(identifier, tagName, deleteRemoteTag);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(
      value = GITLAB_PROJECT_TAGS_BY_IDENTIFIER,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tag> tagProject(
      @PathVariable String identifier, @Valid @RequestBody TagDto tagDto)
      throws URISyntaxException, ProjectNotFoundException {
    Tag tag = projectService.createTag(identifier, tagDto);

    return ResponseEntity.created(
        new URI(
            GITLAB_PROJECT_TAGS_BY_IDENTIFIER.replace("{identifier}", identifier)
                + "/"
                + tag.getTagName()))
        .body(tag);
  }
}
