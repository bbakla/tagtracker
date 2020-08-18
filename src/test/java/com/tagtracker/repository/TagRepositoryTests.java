package com.tagtracker.repository;

import static com.tagtracker.TestSampleCreator.*;

import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TagRepositoryTests {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @AfterEach
  public void cleanUp() {
    tagRepository.deleteAll();
    projectRepository.deleteAll();
  }

  @Test
  public void canAddTagToAProject() throws Exception {
    Project project = createAProjectWithNoDependencies(false);
    Project savedProject = projectRepository.save(project);

    Tag tag = createTag("tagName", savedProject);
    Tag savedTag = tagRepository.save(tag);

    Project savedProjectWithTag = projectRepository
        .findProjectByRemoteProjectId(savedProject.getRemoteProjectId()).get();

    assertEquals(0, savedProject.getTags().size());
    assertEquals(1, savedProjectWithTag.getTags().size());
    assertEquals(tag.getId(), savedProjectWithTag.getTags().iterator().next().getId());
    assertEquals(tag.getTagName(), savedProjectWithTag.getTags().iterator().next().getTagName());
  }

  @Test
  // @Transactional
  public void canDeleteTagOfAProject() throws Exception {
    Project project = createAProjectWithNoDependencies(true);
    projectRepository.save(project);

    String secondTagName = "secondTag";
    Tag secondTag = createTag(secondTagName, project);
    Project savedProject = projectRepository
        .findProjectByRemoteProjectId(project.getRemoteProjectId()).get();
    savedProject.addTag(secondTag);
    Project updatedProject = projectRepository.save(savedProject);
    assertEquals(2, updatedProject.getTags().size());

    tagRepository.deleteByTagName(secondTag.getTagName());

    assertEquals(1, tagRepository.count());

    tagRepository
        .deleteByTagNameAndProject_RemoteProjectId(project.getTags().iterator().next().getTagName(),
            savedProject.getRemoteProjectId());
    assertEquals(0,
        projectRepository.findProjectByRemoteProjectId(project.getRemoteProjectId()).get().getTags()
            .size());
  }

  @Test
  public void canAddDependencyToAProject() {
    Project project = createAProjectWithNoDependencies(true);
    projectRepository.save(project);
    Tag projectTag = project.getTags().iterator().next();

    Project project2 = createAProjectWithNoDependencies(false);
    project2.setProjectName("secondProject");
    project2.setRemoteProjectId("secondRemoteProject");
    project2.addTag(createTag("secondProjectTag", project2));
    projectRepository.save(project2);
    Tag projectTag2 = project2.getTags().iterator().next();

    Project project3 = createAProjectWithNoDependencies(false);
    project3.setProjectName("thirdProject");
    project3.setRemoteProjectId("thirdRemoteProject");
    project3.addTag(createTag("thirdProjectTag", project3));
    projectRepository.save(project3);
    Tag projectTag3 = project3.getTags().iterator().next();

    projectTag.addDependency(projectTag2);
    projectTag.addDependency(projectTag3);
    Tag savedProjectTag = tagRepository.save(projectTag);
    Tag updatedtag2 = tagRepository.save(projectTag2);
    Tag updatedTag3 = tagRepository.save(projectTag3);

    assertTrue(savedProjectTag.getDependentOn().size() == 2);

    Tag tagOfProject2 = tagRepository
        .findTagByTagNameAndProjectProjectName(projectTag2.getTagName(), project2.getProjectName());
    assertEquals(1, tagOfProject2.getRelatedTags().size());
  }

  @Test
  public void canSavePipelineInformationOfATag() {
    Project project = createAProjectWithNoDependencies(true);
    Tag tag = project.getTags().iterator().next();

    Job testStageJob1 = createJob("test");
    testStageJob1.setName("testForDev");

    Job testStageJob2 = createJob("test");
    testStageJob2.setName("testForInt");
    testStageJob2.setJobId("222");

    tag.addJobToStage(testStageJob1);
    tag.addJobToStage(testStageJob2);

    Project savedProject = projectRepository.save(project);

    assertEquals(1, savedProject.getTags().iterator().next().getStages().size());
    assertEquals(2,
        savedProject.getTags().iterator().next().getStages().get("test").getJobs().size());

    Optional<Project> getProjectFromDatabase =
        projectRepository.findProjectByRemoteProjectId(project.getRemoteProjectId());

    assertTrue(
        getProjectFromDatabase.get().getTags().iterator().next().getStages().size() == 1);

    Tag tagInDatabase = tagRepository
        .findTagByTagNameAndProject_RemoteProjectId(tag.getTagName(), project.getRemoteProjectId());

    assertEquals(1, tagInDatabase.getStages().size());
    assertEquals(2, tagInDatabase.getStages().get("test").getJobs().size());


  }
}
