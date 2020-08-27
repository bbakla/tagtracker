package com.tagtracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.model.dto.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TagServiceTest {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private TagService tagService;

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
  public void canATagBeDependentOnAnotherTagOfAProject() throws Exception {
    String terraformProjectId = "102943";
    ProjectResource project = projectService
        .saveRemoteProjectRepositoryInformation(terraformProjectId);

    project.getTags().forEach(t -> assertEquals(0, t.getTagsDependentOn().size()));

    String tenant1ProjectId = "116955";
    ProjectResource tenant1Project = projectService
        .saveRemoteProjectRepositoryInformation(tenant1ProjectId); // has only one tag

    DependencyDto dependencyDto = new DependencyDto(
        tenant1Project.getProjectName(),
        tenant1Project.getProjectId(),
        tenant1Project.getTags().iterator().next().getTagName());
    Tag dependsOnATag = tagRepository
        .findTagByTagNameAndProjectProjectName(project.getTags().iterator().next().getTagName(),
            project.getProjectName());
    tagService
        .addATagAsDependency(project.getProjectId(), dependsOnATag.getTagName(), dependencyDto);

    Tag dependsOnATagAfterDependencyAdded = tagRepository
        .findTagByTagNameAndProjectProjectName(project.getTags().iterator().next().getTagName(),
            project.getProjectName());
    Tag dependsOnMeTag = tagRepository.findTagByTagNameAndProjectProjectName(
        tenant1Project.getTags().iterator().next().getTagName(), tenant1Project.getProjectName());

    assertTrue(dependsOnATagAfterDependencyAdded.getDependentOn().stream()
        .anyMatch(d -> d.getTagName().equals(dependsOnMeTag.getTagName())));
    assertTrue(dependsOnMeTag.getRelatedTags().stream()
        .anyMatch(d -> d.getTagName().equals(dependsOnATagAfterDependencyAdded.getTagName())));
  }

  @Test
  public void canATagStoresTheTagsThatAreDependentOnThatTag() throws Exception {
    String tenant1ProjectId = "116955";
    ProjectResource dependentProject = projectService
        .saveRemoteProjectRepositoryInformation(tenant1ProjectId); // has only one tag

    String terraformProjectId = "102943";
    ProjectResource notDependentProject = projectService
        .saveRemoteProjectRepositoryInformation(terraformProjectId);
    Tag notDependentTag = tagRepository.findTagByTagNameAndProjectProjectName(
        notDependentProject.getTags().iterator().next().getTagName(),
        notDependentProject.getProjectName());

    notDependentProject.getTags().forEach(t -> assertEquals(0, t.getTagsDependentOn().size()));

    Tag dependentTag = tagRepository.findTagByTagNameAndProjectProjectName(
        dependentProject.getTags().iterator().next().getTagName(),
        dependentProject.getProjectName());
    dependentTag.addDependency(notDependentTag);
    Tag savedDependentTag = tagRepository.save(dependentTag);



    Tag getMainTagFromRepo = tagRepository
        .findTagByTagNameAndProject_RemoteProjectId(notDependentTag.getTagName(),
            notDependentProject.getProjectId());

    assertTrue(1 == getMainTagFromRepo.getRelatedTags().size());
  }

  @Test
  public void canCreateATag() throws Exception {
    String tenant1ProjectId = "116955";
    ProjectResource project = projectService
        .saveRemoteProjectRepositoryInformation(tenant1ProjectId); // has only one tag

    TagDto tagDto = new TagDto("testTag", "testTagMessage", "testTagReleaseMessage");
    TagResource createdTag = tagService.createTag(project.getProjectId(), tagDto);

    Tag tagInDatabase = tagRepository
        .findTagByTagNameAndProjectProjectName(tagDto.getTagName(), project.getProjectName());
    assertEquals(tagDto.getTagName(), tagInDatabase.getTagName());
    assertEquals(tagDto.getMessage(), tagInDatabase.getMessage());

    Set<Tag> remoteTags = projectService.getTagsOfRemoteRepository(project.getProjectId());
    assertTrue(remoteTags.stream().anyMatch(
        t -> t.getTagName().equals(createdTag.getTagName()) && t.getMessage()
            .equals(createdTag.getMessage())));

    tagService.deleteTag(project.getProjectId(), tagDto.getTagName(), true);
  }

  @Test
  public void canDeleteARemoteTagAndItsTrackInTheDatabase() throws Exception {
    String tenant1ProjectId = "116955";
    ProjectResource project = projectService
        .saveRemoteProjectRepositoryInformation(tenant1ProjectId); // has only one tag

    TagDto tagDto = new TagDto("testTag", "testTagMessage", "testTagReleaseMessage");
    TagResource createdTag = tagService.createTag(project.getProjectId(), tagDto);

    Tag tagInDatabase = tagRepository
        .findTagByTagNameAndProjectProjectName(tagDto.getTagName(), project.getProjectName());
    assertEquals(tagDto.getTagName(), tagInDatabase.getTagName());
    assertEquals(tagDto.getMessage(), tagInDatabase.getMessage());

    Set<Tag> remoteTags = projectService.getTagsOfRemoteRepository(project.getProjectId());
    assertTrue(remoteTags.stream().anyMatch(
        t -> t.getTagName().equals(createdTag.getTagName()) && t.getMessage()
            .equals(createdTag.getMessage())));

    tagService.deleteTag(project.getProjectId(), tagDto.getTagName(), true);

    Tag deletedTag = tagRepository
        .findTagByTagNameAndProject_RemoteProjectId(tagDto.getTagName(), project.getProjectId());
    assertNull(deletedTag);

    Project savedProjectAfterTagDeletion = projectService.getProject(project.getProjectId());
    assertEquals(0, savedProjectAfterTagDeletion.getTags().size());

    assertFalse(remoteTags.stream().anyMatch(
        t -> t.getTagName().equals(createdTag.getTagName()) && t.getMessage()
            .equals(createdTag.getMessage())));

  }
}
