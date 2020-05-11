package com.tagtracker.controller;

import static com.tagtracker.controller.TestConstants.GITLAB_REPO_GET_BY_ID;
import static com.tagtracker.controller.TestConstants.GITLAB_REPO_GET_TAGS;
import static com.tagtracker.controller.TestConstants.GITLAB_REPO_GET_TAG_BY_ID;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tagtracker.service.gitlab.GitlabService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GitControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GitlabService gitlabService;

  private static String testProjectId = "102943";
  private static String testProjectPath = "baris.bakla1/terraform";
  private static String tagName = "v1.1.0";

  @Test
  public void canGetTheProject() throws Exception {
    mockMvc
        .perform(get(String.format(GITLAB_REPO_GET_BY_ID, testProjectId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testProjectId))
        .andExpect(jsonPath("$.path_with_namespace").value((testProjectPath)));
  }

  @Test
  public void canGetTagsOfAProject() throws Exception {
    mockMvc
        .perform(get(String.format(GITLAB_REPO_GET_TAGS, testProjectId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[*].name", hasItem(tagName)));
  }

  @Test
  public void canGetASpecificTagOfAProject() throws Exception {
    mockMvc
        .perform(get(String.format(GITLAB_REPO_GET_TAG_BY_ID, testProjectId, tagName)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(tagName)));
  }
}
