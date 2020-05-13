
package com.tagtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.dto.ProjectDto;
import com.tagtracker.model.entity.Project;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static com.tagtracker.controller.TestConstants.PROJECT_PATH_BY_ID_TO_BE_FORMATTED;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

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

  private static String testProjectId = "102943";
  private static String testProjectPath = "baris.bakla1/terraform";
  private static String tagName = "v1.1.0";

  @Test
  public void doesNotSaveAProjecWithInvalidId() throws Exception {
    ProjectDto project = new ProjectDto();
    project.setIdentifier("notAvailable");

    mockMvc
        .perform(
            post(PROJECT_BASE_PATH)
                .content(convertToJson(project))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void canSaveInformationOfRemoteRepoAsProject() throws Exception {
    ProjectDto project = new ProjectDto();
    project.setIdentifier("102943");

    mockMvc
        .perform(
            post(PROJECT_BASE_PATH)
                .content(convertToJson(project))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.projectId").value(project.getIdentifier()));

    Optional<Project> projectInDatabase = projectRepository
        .findProjectByRemoteProjectId(project.getIdentifier());

    assertFalse(projectInDatabase.isEmpty());
    assertEquals(project.getIdentifier(), projectInDatabase.get().getRemoteProjectId());
  }

  @Test
  public void canGetAProjectByProjectId() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    String path = String.format(PROJECT_PATH_BY_ID_TO_BE_FORMATTED, project.getRemoteProjectId());
    mockMvc
        .perform(get(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedProject.getRemoteProjectId()));
  }

  @Test
  public void canGetAProjectByProjectPath() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedApplication = projectRepository.save(project);

    var path = String.format(PROJECT_PATH_BY_ID_TO_BE_FORMATTED, project.getEncodedPath());
    mockMvc
        .perform(get(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedApplication.getRemoteProjectId()))
        .andExpect(jsonPath("$.encodedPath").value(savedApplication.getEncodedPath()));
  }

  @Test
  public void canDeleteAProject() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(true);
    Project savedProject = projectRepository.save(project);

    Thread.sleep(1000);
    String path = String.format(PROJECT_PATH_BY_ID_TO_BE_FORMATTED, project.getRemoteProjectId());

    mockMvc
        .perform(delete(String.format(path, savedProject.getRemoteProjectId())))
        .andExpect(status().isNoContent());
  }



/*





  @Test
  public void canDeleteTagOfAnApplicationFromDatabaseAndRemoteRepository() throws Exception {
    String testProjectId = "102943";
    String testProjectPath = "baris.bakla1/terraform";
    String tagName = "testTag";

    RemoteTag newTag = gitlabService
        .createTag(testProjectId, new TagDto(tagName, "message for testTag", "* release note"));

    Application application = TestSampleCreator.createApplication();
    application.setProjectId(testProjectId);
    application.setEncodedPath(testProjectPath);
    application.getTags().setTagName(tagName);
    Application savedApplication = applicationRepository.save(application);

    mockMvc
        .perform(delete(String
            .format(APPLICATION_PATH_DELETE_TAG_BY_NAME, savedApplication.getProjectId(),
                savedApplication.getTags().getTagName(), true)))
        .andExpect(status().isNoContent());

    assertNull(applicationRepository
        .findApplicationByProjectIdAndTagTagName(savedApplication.getProjectId(),
            savedApplication.getTags().getTagName()));

    assertThrows(TagNotFoundException.class,
        () -> gitlabService.getTagOfARepo(testProjectId, tagName));
  }


  @Test
  public void canTagARepository() throws Exception {
    Application application = TestSampleCreator.createApplication();
    application.setProjectId(testProjectId);
    application.setEncodedPath(testProjectPath);
    application.getTags().setTagName(tagName);
    Application savedApplication = applicationRepository.save(application);

    TagDto tagDto = new TagDto("controllerTestTag", "controllerTestMessage", "releaseNotes");
    try {
      mockMvc
          .perform(
              post(String.format(APPLICATION_CREATE_TAGS, testProjectId))
                  .content(convertToJson(tagDto))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.tagName", is(tagDto.getTagName())))
          .andExpect(jsonPath("$.message", is(tagDto.getMessage())));

      List<Tag> tags = tagRepository.findAllByApplication_ProjectId(testProjectId);

      assertEquals(2, tags.size());
      assertTrue(tags.stream().anyMatch(t -> t.getTagName().equals(tagDto.getTagName())));

      Thread.sleep(1000);

    } finally {
      gitlabService.deleteTag(testProjectId, tagDto.getTagName());
    }
  }
 */

  private String convertToJson(ProjectDto application) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(application);
  }

/*  private String convertToJson(TagDto tagDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(tagDto);
  }*/


}

