package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;
import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH;
import static com.tagtracker.controller.Constants.PROJECT_TAG_BY_NAME;
import static com.tagtracker.controller.Constants.PROJECT_PATH_TO_DEPLOY;

import com.tagtracker.model.dto.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.service.ProjectNotFoundException;
import com.tagtracker.service.TagService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PROJECT_BASE_PATH)
public class TagController {

  @Autowired
  private TagService tagService;

  @PutMapping(PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH)
  public ResponseEntity<TagResource> addDependency(
      @RequestHeader("X-operation") String operation,
      @PathVariable String identifier,
      @PathVariable String tagName,
      @RequestBody DependencyDto dependentTo)
      throws ProjectNotFoundException {

    TagResource tagResource = null;
    if (operation.equals("save")) {
      tagResource = tagService.addATagAsDependency(identifier, tagName, dependentTo);
    } else if (operation.equals("remove")) {
      tagResource = tagService.removeTagFromDependencies(identifier, tagName, dependentTo);
    }

    return ResponseEntity.ok().body(tagResource);
  }

  /*  @PatchMapping(PROJECT_PATH_BY_ID_AND_DEPENDENT_ON_ME_PATH)
  public ResponseEntity<TagResource> dependentOnMe(
      @PathVariable String identifier,
      @PathVariable String tagName,
      @RequestBody DependencyDto dependentOnMe)
      throws ProjectNotFoundException {
    TagResource tagResource = tagService.addATagAsDependentOnMe(identifier, tagName, dependentOnMe);

    return ResponseEntity.ok().body(tagResource);
  }*/

  @PatchMapping(PROJECT_PATH_TO_DEPLOY)
  public ResponseEntity<TagResource> deployTo(
      @PathVariable String identifier,
      @PathVariable String tagName,
      @PathVariable Environment environment)
      throws ProjectNotFoundException {
    TagResource tagResource = tagService.deploy(identifier, tagName, environment);

    return ResponseEntity.ok().body(tagResource);
  }

  @PostMapping(
      value = GITLAB_PROJECT_TAGS_BY_IDENTIFIER,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TagResource> tagProject(
      @PathVariable String identifier, @Valid @RequestBody TagDto tagDto)
      throws URISyntaxException, ProjectNotFoundException {
    TagResource tag = tagService.createTag(identifier, tagDto);

    return ResponseEntity.created(
        new URI(
            GITLAB_PROJECT_TAGS_BY_IDENTIFIER.replace("{identifier}", identifier)
                + "/"
                + tag.getTagName()))
        .body(tag);
  }

  @DeleteMapping(PROJECT_TAG_BY_NAME)
  public ResponseEntity<?> deleteTag(
      @PathVariable String identifier,
      @PathVariable String tagName,
      @RequestParam(defaultValue = "true", required = true) Boolean deleteRemoteTag)
      throws ProjectNotFoundException {

    tagService.deleteTag(identifier, tagName, deleteRemoteTag);

    return ResponseEntity.noContent().build();
  }

  @GetMapping(GITLAB_PROJECT_TAGS_BY_IDENTIFIER)
  public ResponseEntity<List<TagResource>> getTagsOfAProject(@PathVariable String identifier)
      throws ProjectNotFoundException {
    List<TagResource> tags = tagService.getTagsOfAProject(identifier);

    return ResponseEntity.ok().body(tags);
  }

  @GetMapping(PROJECT_TAG_BY_NAME)
  public ResponseEntity<TagResource> getSpecificTagOfAProject(
      @PathVariable String identifier, @PathVariable String tagName)
      throws ProjectNotFoundException {

    TagResource tag = tagService.getTagOfAProject(identifier, tagName);

    return ResponseEntity.ok().body(tag);
  }
}
