
package com.tagtracker.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.model.entity.Project;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.repository.ProjectRepository;
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

  @AfterEach
  public void cleanup() {
    projectRepository.deleteAll();
  }

  @Test
  public void canSaveTheApplicationAvailableInRemoteRepoById() throws Exception {
    String applicationId = "102943";
    ProjectResource project = projectService.saveProject(applicationId);

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
  public void canSaveTheApplicationAvailableInRemoteRepoByProjectPathWithNamespace()
      throws Exception {
    String projectNamespace = "baris.bakla1/terraform";

    ProjectResource project = projectService.saveProject(projectNamespace);

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
        ProjectNotFoundException.class, () -> projectService.saveProject(projectId));
  }
}

