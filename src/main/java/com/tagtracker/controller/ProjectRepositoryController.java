package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.PROJECT_PATH_BY_ID;
import static com.tagtracker.controller.Constants.PROJECT_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_TAGS_BY_IDENTIFIER;

import com.tagtracker.model.dto.ProjectDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.service.ProjectService;
import com.tagtracker.service.ProjectNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public ResponseEntity<List<ProjectResource>> getProjects() {
    List<ProjectResource> projects = projectService.getProjects();

    return ResponseEntity.ok().body(projects);
  }

  @PostMapping()
  public ResponseEntity<ProjectResource> saveApplication(
      @Valid @RequestBody ProjectDto projectDto)
      throws ProjectNotFoundException, URISyntaxException {

    ProjectResource project =
        projectService.saveRemoteProjectRepositoryInformation(projectDto.getIdentifier());

    return ResponseEntity.created(new URI(PROJECT_BASE_PATH + "/" + project.getProjectId()))
        .body(project);
  }

  @DeleteMapping(PROJECT_PATH_BY_ID)
  public ResponseEntity<?> deleteApplication(@PathVariable String identifier)
      throws ProjectNotFoundException {
    projectService.deleteProject(identifier);

    return ResponseEntity.noContent().build();
  }

}
