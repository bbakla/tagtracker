package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.APPLICATION_BASE_PATH;
import static com.tagtracker.controller.Constants.APPLICATION_PATH_BY_ID;
import static com.tagtracker.controller.Constants.APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH;
import static com.tagtracker.controller.Constants.APPLICATION_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.Constants.APPLICATION_PATH_TO_DEPLOY;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;

import com.tagtracker.model.dto.ApplicationDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.service.ApplicationService;
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
@RequestMapping(APPLICATION_BASE_PATH)
public class ApplicationController {

  @Autowired
  private ApplicationService applicationService;

  @GetMapping(APPLICATION_PATH_BY_ID)
  public ResponseEntity<ApplicationResource> getApplication(@PathVariable String identifier)
      throws ProjectNotFoundException {
    ApplicationResource application = applicationService.getByProjectIdOrPath(identifier);

    return ResponseEntity.ok().body(application);
  }

  @PostMapping()
  public ResponseEntity<ApplicationResource> saveApplication(
      @Valid @RequestBody ApplicationDto applicationDto)
      throws ProjectNotFoundException, URISyntaxException {

    ApplicationResource application =
        applicationService.saveApplication(applicationDto.getIdentifier());

    return ResponseEntity.created(new URI(APPLICATION_BASE_PATH + "/" + application.getProjectId()))
        .body(application);
  }

  @PatchMapping(APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH)
  public ResponseEntity<ApplicationResource> addDependency(
      @PathVariable String identifier, @PathVariable String dependentTo)
      throws ProjectNotFoundException {

    ApplicationResource application = applicationService.addDependency(identifier, dependentTo);

    return ResponseEntity.ok().body(application);
  }

  @PatchMapping(APPLICATION_PATH_TO_DEPLOY)
  public ResponseEntity<ApplicationResource> deployTo(
      @PathVariable String identifier, @PathVariable Environment environment)
      throws ProjectNotFoundException {
    ApplicationResource applicationResource = applicationService.deploy(identifier, environment);

    return ResponseEntity.ok().body(applicationResource);
  }

  @DeleteMapping(APPLICATION_PATH_BY_ID)
  public ResponseEntity<?> deleteApplication(@PathVariable String identifier)
      throws ProjectNotFoundException {
    applicationService.deleteApp(identifier);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(APPLICATION_PATH_DELETE_TAG_BY_NAME)
  public ResponseEntity<?> deleteTag(
      @PathVariable String identifier,
      @PathVariable String tagName,
      @RequestParam(defaultValue = "true", required = true) Boolean deleteRemoteTag)
      throws ProjectNotFoundException {

    applicationService.deleteTag(identifier, tagName, deleteRemoteTag);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(
      value = GITLAB_PROJECT_TAGS_BY_IDENTIFIER,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tag> tagProject(
      @PathVariable String identifier, @Valid @RequestBody TagDto tagDto)
      throws URISyntaxException, ProjectNotFoundException {
    Tag tag = applicationService.createTag(identifier, tagDto);

    return ResponseEntity.created(
        new URI(
            GITLAB_PROJECT_TAGS_BY_IDENTIFIER.replace("{identifier}", identifier)
                + "/"
                + tag.getTagName()))
        .body(tag);
  }
}
