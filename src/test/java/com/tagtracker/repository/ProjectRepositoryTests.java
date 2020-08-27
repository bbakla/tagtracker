package com.tagtracker.repository;

import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProjectRepositoryTests {

  @Autowired
  private ProjectRepository projectRepository;

  @AfterEach
  public void cleanUp() {
    projectRepository.deleteAll();
  }

  @Test
  public void canSaveAProject() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    assertNotNull(savedProject.getRemoteProjectId());
    assertEquals(project.getProjectName(), savedProject.getProjectName());
    assertNotNull(savedProject.getCreatedDate());
    assertNotNull(savedProject.getLastModifiedDate());
    project.getTags().forEach(t -> assertNotNull(t.getId()));
    assertEquals(project.getTags(), savedProject.getTags());
  }

  @Test
  public void cannotCreateAnApplicationWithTheSameProjectId() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    assertEquals(project.getProjectName(), savedProject.getProjectName());
    assertEquals(project.getTags(), savedProject.getTags());

    Project project1 = new Project(project.getRemoteProjectId(), project.getProjectName());
    assertThrows(DataIntegrityViolationException.class, () -> projectRepository.save(project1));
  }

  @Test
  public void canGetAnApplicationFromDatabaseById() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    Optional<Project> applicationInDatabase =
        projectRepository.findProjectByRemoteProjectId(savedProject.getRemoteProjectId());

    assertNotNull(applicationInDatabase.get().getRemoteProjectId());
    assertEquals(project.getProjectName(), applicationInDatabase.get().getProjectName());
    assertEquals(project.getRemoteProjectId(), applicationInDatabase.get().getRemoteProjectId());
    assertNotNull(savedProject.getLastModifiedDate());
    project.getTags().forEach(t -> assertNotNull(t.getId()));
    assertEquals(project.getTags().size(), applicationInDatabase.get().getTags().size());
  }

  @Test
  public void canGetAnApplicationByTagAndId() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    projectRepository.save(project);

    Project project2 = TestSampleCreator.createAProjectWithNoDependencies(true);
    project2.setRemoteProjectId("secondAppId");
    project2.setProjectName("secondApplicationName");

    Project savedProject2 = projectRepository.save(project2);

    Project getProjectByIdandTag =
        projectRepository.findProjectByRemoteProjectIdAndTagsTagName(
            savedProject2.getRemoteProjectId(),
            savedProject2.getTags().iterator().next().getTagName());

    assertNotNull(getProjectByIdandTag.getRemoteProjectId());
    assertEquals(project2.getProjectName(), getProjectByIdandTag.getProjectName());
    assertEquals(
        project2.getTags().iterator().next().getTagName(),
        getProjectByIdandTag.getTags().iterator().next().getTagName());
    assertEquals(
        project2.getTags().iterator().next().getMessage(),
        getProjectByIdandTag.getTags().iterator().next().getMessage());
  }

  @Test
  public void cannotCreateATagWithTheSameNameToAnApplication() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    assertEquals(project.getProjectName(), savedProject.getProjectName());
    assertEquals(project.getTags(), savedProject.getTags());

    Project project1 = TestSampleCreator.createAProjectWithNoDependencies(true);
    assertThrows(DataIntegrityViolationException.class, () -> projectRepository.save(project1));
  }

  @Test
  public void canDeleteAProjectUsingProjectId() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    Project project2 = TestSampleCreator.createAProjectWithNoDependencies(true);
    project2.setRemoteProjectId("projectId2");

    String secondTagName = "secondTag";
    Tag sampleTag = TestSampleCreator.createTag(secondTagName, project2);
    project2.addTag(sampleTag);

    Project savedProjectWithTag = projectRepository.save(project2);
    List<Project> projectList = projectRepository.findAll();
    assertEquals(2, projectList.size());

    projectRepository.deleteByRemoteProjectId(savedProject.getRemoteProjectId());

    assertEquals(1, projectRepository.findAll().size());
  }

  @Test
  public void canAddMultipleTagsToAProject() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    String secondTagName = "secondTag";
    Tag sampleTag = TestSampleCreator.createTag(secondTagName, project);
    project.addTag(sampleTag);

    Project updatedProject = projectRepository.save(project);

    assertEquals(2, updatedProject.getTags().size());
    assertTrue(
        updatedProject.getTags().stream().anyMatch(t -> t.getTagName().equals(secondTagName)));
  }

  @Test
  public void canFindTheTagOfAProjectUsingTagNameAndProjectId() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    String secondTagName = "secondTag";
    Tag sampleTag = TestSampleCreator.createTag(secondTagName, project);
    project.addTag(sampleTag);
    projectRepository.save(project);

    Project savedProject = projectRepository
        .findProjectByRemoteProjectId(project.getRemoteProjectId()).get();

    String firstExpectedTagName = project.getTags().iterator().next().getTagName();

    assertEquals(firstExpectedTagName, savedProject.findTag(firstExpectedTagName).getTagName());

    assertEquals(secondTagName, savedProject.findTag(secondTagName).getTagName());
  }

  @Test
  // @Transactional
  public void canDeleteTagOfAProject() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    projectRepository.save(project);

    String secondTagName = "secondTag";
    Tag secondTag = TestSampleCreator.createTag(secondTagName, project);
    Project savedProject = projectRepository
        .findProjectByRemoteProjectId(project.getRemoteProjectId()).get();
    savedProject.addTag(secondTag);
    Project updatedProject = projectRepository.save(savedProject);

    assertEquals(2, updatedProject.getTags().size());

    updatedProject.removeTag(secondTagName);
    Project tagRemovedApp = projectRepository.save(updatedProject);

    assertEquals(1, projectRepository.findAll().size());
  }

  @Test
  public void canBeDependentOnOtherServices() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(false);
    Tag tagForApplication = TestSampleCreator.createTag("tagName", project);
    project.addTag(tagForApplication);
    Project savedProject = projectRepository.save(project);

    Project project2 = TestSampleCreator.createAProjectWithNoDependencies(false);
    project2.setRemoteProjectId("Independent");
    project2.setProjectName("independentProject");
    project2.setEncodedPath("/independent");
    Tag tagForApplication2 = TestSampleCreator.createTag("tagForSecondApp", project2);
    project2.addTag(tagForApplication2);
    Project savedProject2 = projectRepository.save(project2);

    Tag savedTagForApplication = savedProject.findTag(tagForApplication.getTagName());
    savedTagForApplication.addDependency(savedProject2.findTag(tagForApplication2.getTagName()));

    savedProject = projectRepository.save(savedProject);

    assertEquals(1, savedProject.getTags().iterator().next().getDependentOn().size());
    assertEquals(
        tagForApplication2.getTagName(),
        savedProject.getTags().iterator().next().getDependentOn().iterator().next().getTagName());
  }
}
