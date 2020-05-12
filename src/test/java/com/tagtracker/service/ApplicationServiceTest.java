
package com.tagtracker.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.controller.DependencyDto;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationServiceTest {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TagRepository tagRepository;

  @AfterEach
  public void cleanup() {
    projectRepository.deleteAll();
    tagRepository.deleteAll();
  }

  @Test
  public void canSaveTheApplicationIntoDatabaseByFindingAtRemoteSideByProjectId() throws Exception {
    String applicationId = "102943";
    ProjectResource project = projectService.saveRemoteProjectRepositoryInformation(applicationId);

    Optional<Project> projectInDatabase =
        projectRepository.findProjectByProjectId(applicationId);

    assertEquals(applicationId, projectInDatabase.get().getProjectId());
    assertEquals(project.getEncodedPath(), projectInDatabase.get().getEncodedPath());
    assertEquals(
        projectInDatabase.get().getTags().size(), project.getTags().size());

    projectInDatabase.get().getTags().forEach(t -> assertTrue(project.getTags().stream()
        .anyMatch(tagInProject -> tagInProject.getTagName().equals(t.getTagName()))));
  }

  @Test
  public void canSaveTheApplicationIntoDatabaseByFindingAtRemoteSideByProjectNamespace()
      throws Exception {
    String projectNamespace = "baris.bakla1/terraform";

    ProjectResource project = projectService
        .saveRemoteProjectRepositoryInformation(projectNamespace);

    Optional<Project> projectInDatabase =
        projectRepository.findProjectByEncodedPath(project.getEncodedPath());

    assertEquals(project.getProjectId(), projectInDatabase.get().getProjectId());
    assertEquals(project.getEncodedPath(), projectInDatabase.get().getEncodedPath());
    assertEquals(
        projectInDatabase.get().getTags().size(), project.getTags().size());

    projectInDatabase.get().getTags().forEach(t -> assertTrue(project.getTags().stream()
        .anyMatch(tagInProject -> tagInProject.getTagName().equals(t.getTagName()))));
  }

  @Test
  public void returnsExceptionWhenApplicationBeingSavedIsNotFoundInRemoteRepo() throws Exception {
    String projectId = "NoProject";
    assertThrows(
        ProjectNotFoundException.class,
        () -> projectService.saveRemoteProjectRepositoryInformation(projectId));
  }

  @Test
  public void canGetProjectFromRemoteSideByProjectId() throws Exception {
    String projectId = "102943";
    ProjectResource project = projectService.saveRemoteProjectRepositoryInformation(projectId);

    ProjectResource projectResourceGetById = projectService.getByProjectIdOrPath(projectId);

    assertEquals(project.getProjectId(), projectResourceGetById.getProjectId());
    assertEquals(project.getTags().size(), projectResourceGetById.getTags().size());
    assertEquals(project.getEncodedPath(), projectResourceGetById.getEncodedPath());
  }




}

