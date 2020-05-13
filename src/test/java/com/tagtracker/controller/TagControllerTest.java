package com.tagtracker.controller;

import static com.tagtracker.controller.TestConstants.APPLICATION_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.TestConstants.PROJECT_DEPLOY_PATH_TEMPLATE;
import static com.tagtracker.controller.TestConstants.PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import org.hamcrest.Matchers;
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
    assertEquals(1, notDependentTagInDatabase.getDependentOnMe().size());
  }

  @Test
  public void canDeployATagToAnEnvironment() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    String path = String
        .format(PROJECT_DEPLOY_PATH_TEMPLATE, project.getRemoteProjectId(),
            savedProject.getTags().iterator().next().getTagName(),
            Environment.DEV);

    mockMvc
        .perform(patch(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedProject.getRemoteProjectId()))
        .andExpect(jsonPath("$.deployedEnvironments", Matchers.aMapWithSize(1)))
        .andExpect(
            jsonPath("$.deployedEnvironments", Matchers.hasEntry(Environment.DEV.name(), true)));

    Tag tag = tagRepository.findTagByTagNameAndProjectProjectName(
        savedProject.getTags().iterator().next().getTagName(), savedProject.getProjectName());

    assertEquals(1, tag.getDeployedEnvironments().size());
    assertTrue(tag.getDeployedEnvironments().get(Environment.DEV));
    assertNull(tag.getDeployedEnvironments().get(Environment.INT));
    assertNull(tag.getDeployedEnvironments().get(Environment.PROD));
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
        .format(APPLICATION_PATH_DELETE_TAG_BY_NAME, savedProject.getRemoteProjectId(),
            tagName, false);

    mockMvc
        .perform(delete(String
            .format(path)))
        .andExpect(status().isNoContent());

    assertNull(
        tagRepository.findTagByTagNameAndProjectProjectName(tagName, project.getProjectName()));

  }

  private String convertTDependencyDtoToJson(DependencyDto dependencyDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(dependencyDto);
  }
}
