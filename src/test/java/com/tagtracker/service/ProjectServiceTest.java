
package com.tagtracker.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProjectServiceTest {

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
        projectRepository.findProjectByRemoteProjectId(applicationId);

    assertEquals(applicationId, projectInDatabase.get().getRemoteProjectId());
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

    assertEquals(project.getProjectId(), projectInDatabase.get().getRemoteProjectId());
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

  @Test
  public void canGetAllTagJobsOfAProject() throws Exception {
    String projectId = "135330";

    Map<JobKey, Set<Job>> jobs = projectService.getTagJobs(projectId);

    jobs.entrySet()
        .forEach(
            e -> {
              assertTrue(e.getKey().getTagName().startsWith("v"));
              assertNotNull(e.getValue());

              if (e.getKey().getStage().equals("build")) {
                assertEquals(2, e.getValue().size());
              }
            });
  }

  @Test
  public void canGetTheOrderOfStages() throws Exception {
    String projectId = "135330";

    var stages = projectService.readStageOrder(projectId);

    assertTrue(stages.get(0).equals("build-runner"));
    assertTrue(stages.get(1).equals("build"));
    assertTrue(stages.get(2).equals("test"));
    assertTrue(stages.get(3).equals("qualitygate"));

  }


}

