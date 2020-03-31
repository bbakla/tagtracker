package com.tagtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.dto.ApplicationDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Application;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import com.tagtracker.repository.ApplicationRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import com.tagtracker.service.gitlab.TagNotFoundException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.tagtracker.controller.Constants.APPLICATION_BASE_PATH;
import static com.tagtracker.controller.TestConstants.APPLICATION_CREATE_TAGS;
import static com.tagtracker.controller.TestConstants.APPLICATION_DEPLOY_PATH_TEMPLATE;
import static com.tagtracker.controller.TestConstants.APPLICATION_PATH_BY_ID;
import static com.tagtracker.controller.TestConstants.APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE;
import static com.tagtracker.controller.TestConstants.APPLICATION_PATH_DELETE_TAG_BY_NAME;
import static com.tagtracker.controller.TestConstants.GITLAB_REPO_GET_TAGS;
import static com.tagtracker.controller.TestConstants.GITLAB_REPO_GET_TAG_BY_ID;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private GitlabService gitlabService;

  @AfterEach
  public void clean() {
    applicationRepository.deleteAll();
  }

  private static String testProjectId = "102943";
  private static String testProjectPath = "baris.bakla1/terraform";
  private static String tagName = "v1.1.0";

  @Test
  public void doesNotSaveAnApplicationWithInvalidId() throws Exception {
    ApplicationDto application = new ApplicationDto();
    application.setIdentifier("notAvailable");

    mockMvc
        .perform(
            post(APPLICATION_BASE_PATH)
                .content(convertToJson(application))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void canSaveAnApplicationWhichExistsInRemoteRepository() throws Exception {
    ApplicationDto application = new ApplicationDto();
    application.setIdentifier("102943");

    mockMvc
        .perform(
            post(APPLICATION_BASE_PATH)
                .content(convertToJson(application))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.projectId").value(application.getIdentifier()));
  }

  @Test
  public void canGetAnApplicationByProjectId() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    java.lang.String path = APPLICATION_BASE_PATH + "/" + application.getProjectId();
    mockMvc
        .perform(get(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedApplication.getProjectId()));
  }

  @Test
  public void canGetAnApplicationByProjectPath() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    java.lang.String path = APPLICATION_BASE_PATH + "/" + application.getEncodedPath();
    mockMvc
        .perform(get(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedApplication.getProjectId()))
        .andExpect(jsonPath("$.encodedPath").value(savedApplication.getEncodedPath()));
  }

  @Test
  public void canAddAnAppDependency() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Application application2 = TestSampleCreator.createApplication();
    application2.setProjectId("secondProject_ID");
    application2.setApplicationName("secondProject");
    application2.setEncodedPath("path/secondProject");
    Application savedApplication2 = applicationRepository.save(application2);

    String path = String
        .format(APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE, application.getProjectId(),
            application2.getProjectId());

    mockMvc
        .perform(patch(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedApplication.getProjectId()))
        .andExpect(jsonPath("$.dependentTo[0].projectId").value(application2.getProjectId()));
  }

  @Test
  public void canDeployToAnEnvironment() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    String path = String
        .format(APPLICATION_DEPLOY_PATH_TEMPLATE, application.getProjectId(),
            Environment.DEV);

    mockMvc
        .perform(patch(path))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.projectId").value(savedApplication.getProjectId()))
        .andExpect(jsonPath("$.deployedEnvironments", Matchers.aMapWithSize(1)))
        .andExpect(
            jsonPath("$.deployedEnvironments", Matchers.hasEntry(Environment.DEV.name(), true)));
  }


  @Test
  public void canDeleteAnApplication() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Thread.sleep(1000);

    mockMvc
        .perform(delete(String.format(APPLICATION_PATH_BY_ID, savedApplication.getProjectId())))
        .andExpect(status().isNoContent());
  }

  @Test
  public void canDeleteTagOfAnApplicationFromDatabase() throws Exception {
    String testProjectId = "102943";
    String testProjectPath = "baris.bakla1/terraform";
    String tagName = "testTag";

    //Tag newTag = gitlabService.createTag("102943", new TagDto(tagName, "message for testTag", "* release note"));

    Application application = TestSampleCreator.createApplication();
    application.setProjectId(testProjectId);
    application.setEncodedPath(testProjectPath);
    application.getTag().setTagName(tagName);
    Application savedApplication = applicationRepository.save(application);

    mockMvc
        .perform(delete(String
            .format(APPLICATION_PATH_DELETE_TAG_BY_NAME, savedApplication.getProjectId(),
                savedApplication.getTag().getTagName(), false)))
        .andExpect(status().isNoContent());

    assertNull(applicationRepository
        .findApplicationByProjectIdAndTagTagName(savedApplication.getProjectId(),
            savedApplication.getTag().getTagName()));
  }

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
    application.getTag().setTagName(tagName);
    Application savedApplication = applicationRepository.save(application);

    mockMvc
        .perform(delete(String
            .format(APPLICATION_PATH_DELETE_TAG_BY_NAME, savedApplication.getProjectId(),
                savedApplication.getTag().getTagName(), true)))
        .andExpect(status().isNoContent());

    assertNull(applicationRepository
        .findApplicationByProjectIdAndTagTagName(savedApplication.getProjectId(),
            savedApplication.getTag().getTagName()));

    assertThrows(TagNotFoundException.class,
        () -> gitlabService.getTagOfARepo(testProjectId, tagName));
  }


  @Test
  public void canTagARepository() throws Exception {
    Application application = TestSampleCreator.createApplication();
    application.setProjectId(testProjectId);
    application.setEncodedPath(testProjectPath);
    application.getTag().setTagName(tagName);
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


  private String convertToJson(ApplicationDto application) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(application);
  }

  private String convertToJson(TagDto tagDto) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(tagDto);
  }
}
