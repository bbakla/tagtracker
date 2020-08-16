package com.tagtracker.controller;

import static com.tagtracker.controller.TestConstants.PROJECT_CREATE_TAGS;
import static com.tagtracker.controller.TestConstants.PROJECT_PATH_BY_ID_AND_DEPENDENT_TO_ME_TO_BE_FORMATTED;
import static com.tagtracker.controller.TestConstants.PROJECT_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.TestConstants.PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.dto.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import com.tagtracker.service.TagNotFoundException;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private GitlabService gitlabService;

  @AfterEach
  public void clean() {
    projectRepository.deleteAll();
    tagRepository.deleteAll();
  }

  @Test
  public void canAddDependencyToTheTagOfAProject() throws Exception {
    // GIVEN
    Project dependentProject = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedDependentProject = projectRepository.save(dependentProject);

    Project notDependentProject = TestSampleCreator.createAProjectWithNoDependencies(true);
    notDependentProject.setRemoteProjectId("secondProject_ID");
    notDependentProject.setProjectName("secondProject");
    notDependentProject.setEncodedPath("path/secondProject");
    Project savedApplication2 = projectRepository.save(notDependentProject);

    String path =
        String.format(
            PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE,
            dependentProject.getRemoteProjectId(),
            savedDependentProject.getTags().iterator().next().getTagName());

    DependencyDto dependencyDto =
        new DependencyDto(
            notDependentProject.getProjectName(),
            notDependentProject.getRemoteProjectId(),
            notDependentProject.getTags().iterator().next().getTagName());

    // WHEN
    mockMvc
        .perform(
            patch(path)
                .content(convertTDependencyDtoToJson(dependencyDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedDependentProject.getRemoteProjectId()))
        .andExpect(
            jsonPath("$.tagsDependentOn[0].projectId")
                .value(notDependentProject.getRemoteProjectId()));

    // THEN
    Tag dependentTag =
        tagRepository.findTagByTagNameAndProjectProjectName(
            savedDependentProject.getTags().iterator().next().getTagName(),
            savedDependentProject.getProjectName());
    assertNotNull(dependentTag.getTagName());
    assertEquals(1, dependentTag.getDependentOn().size());

    Tag notDependentTagInDatabase =
        tagRepository.findTagByTagNameAndProjectProjectName(
            dependencyDto.getTagName(), dependencyDto.getProjectName());
    assertEquals(1, notDependentTagInDatabase.getRelatedTags().size());
  }

  @Test
  public void canATagKeepsTheTrackOfTheTagsThatAreDependentOnIt() throws Exception {
    // GIVEN
    Project dependentProject = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedDependentProject = projectRepository.save(dependentProject);

    Project notDependentProject = TestSampleCreator.createAProjectWithNoDependencies(false);
    notDependentProject.setRemoteProjectId("secondProject_ID");
    notDependentProject.setProjectName("secondProject");
    notDependentProject.setEncodedPath("path/secondProject");
    Tag notDependentTag = TestSampleCreator
        .createTag("OtherIneIsDependentOnMe", notDependentProject);
    notDependentProject.addTag(notDependentTag);

    Project savedNotDependentProject = projectRepository.save(notDependentProject);

    String path =
        String.format(
            PROJECT_PATH_BY_ID_AND_DEPENDENT_TO_ME_TO_BE_FORMATTED,
            notDependentProject.getRemoteProjectId(),
            savedNotDependentProject.getTags().iterator().next().getTagName());

    DependencyDto dependencyDto =
        new DependencyDto(
            dependentProject.getProjectName(),
            dependentProject.getRemoteProjectId(),
            dependentProject.getTags().iterator().next().getTagName());

    // WHEN
    mockMvc
        .perform(
            patch(path)
                .content(convertTDependencyDtoToJson(dependencyDto))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(notDependentProject.getRemoteProjectId()))
        .andExpect(
            jsonPath("$.tagsDependentOnMe[0].projectId")
                .value(dependentProject.getRemoteProjectId()));

    // THEN
    Tag dependentTag =
        tagRepository.findTagByTagNameAndProjectProjectName(
            savedDependentProject.getTags().iterator().next().getTagName(),
            savedDependentProject.getProjectName());
    assertNotNull(dependentTag.getTagName());
    assertEquals(1, dependentTag.getDependentOn().size());

    Tag notDependentTagInDatabase =
        tagRepository.findTagByTagNameAndProjectProjectName(
            notDependentTag.getTagName(),
            notDependentTag.getProject().getProjectName());
    assertEquals(1, notDependentTagInDatabase.getRelatedTags().size());
  }


  @Test
  public void canDeleteTagOfAProjectFromDatabase() throws Exception {
    String testProjectId = "102943";
    String testProjectPath = "baris.bakla1/terraform";

    //Tag newTag = gitlabService.createTag("102943", new TagDto(tagName, "message for testTag", "* release note"));

    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    project.setRemoteProjectId(testProjectId);
    project.setEncodedPath(testProjectPath);
    Project savedProject = projectRepository.save(project);

    String tagName = savedProject.getTags().iterator().next().getTagName();
    String path = String
        .format(PROJECT_PATH_DELETE_TAG_BY_NAME, savedProject.getRemoteProjectId(),
            tagName, false);

    mockMvc
        .perform(delete(String
            .format(path)))
        .andExpect(status().isNoContent());

    assertNull(
        tagRepository.findTagByTagNameAndProjectProjectName(tagName, project.getProjectName()));
  }


  @Test
  public void canDeleteTagOfAProjectFromDatabaseAndRemoteRepository() throws Exception {
    String testProjectId = "102943";
    String testProjectPath = "baris.bakla1/terraform";
    String tagName = "testTag";

    GitlabTag newTag = gitlabService
        .createTag(testProjectId, new TagDto(tagName, "message for testTag", "* release note"));

    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    project.setRemoteProjectId(testProjectId);
    project.setEncodedPath(testProjectPath);
    project.getTags().iterator().next().setTagName(tagName);
    Project savedProject = projectRepository.save(project);

    mockMvc
        .perform(delete(String
            .format(PROJECT_PATH_DELETE_TAG_BY_NAME, savedProject.getRemoteProjectId(),
                savedProject.getTags().iterator().next().getTagName(), true)))
        .andExpect(status().isNoContent());

    assertNull(projectRepository.findProjectByRemoteProjectIdAndTagsTagName(
        savedProject.getRemoteProjectId(),
        savedProject.getTags().iterator().next().getTagName()));

    assertThrows(TagNotFoundException.class,
        () -> gitlabService.getTagOfARemoteRepository(testProjectId, tagName));
  }

  @Test
  public void canTagARepository() throws Exception {
    String testProjectId = "102943";
    String testProjectPath = "baris.bakla1/terraform";
    String tagName = "testTag";

    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    project.setRemoteProjectId(testProjectId);
    project.setEncodedPath(testProjectPath);
    project.getTags().iterator().next().setTagName(tagName);
    Project savedApplication = projectRepository.save(project);

    TagDto tagDto = new TagDto("controllerTestTag", "controllerTestMessage", "releaseNotes");
    try {
      mockMvc
          .perform(
              post(String.format(PROJECT_CREATE_TAGS, testProjectId))
                  .content(convertToJson(tagDto))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.tagName").value(tagDto.getTagName()))
          .andExpect(jsonPath("$.message").value(tagDto.getMessage()));

      Set<Tag> tags = projectRepository.findProjectByRemoteProjectId(testProjectId).get().getTags();

      assertEquals(2, tags.size());
      assertTrue(tags.stream().anyMatch(t -> t.getTagName().equals(tagDto.getTagName())));

      Thread.sleep(1000);

    } finally {
      gitlabService.deleteTag(testProjectId, tagDto.getTagName());
    }
  }

  private String convertTDependencyDtoToJson(DependencyDto dependencyDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(dependencyDto);
  }

  private String convertToJson(TagDto tagDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(tagDto);
  }
}
