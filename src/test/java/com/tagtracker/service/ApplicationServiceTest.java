package com.tagtracker.service;

import com.tagtracker.model.entity.Application;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.repository.ApplicationRepository;
import com.tagtracker.service.ApplicationService;
import com.tagtracker.service.ProjectNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

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
  private ApplicationService applicationService;

  @Autowired
  private ApplicationRepository applicationRepository;

  @AfterEach
  public void cleanup() {
    applicationRepository.deleteAll();
  }

  @Test
  public void canSaveTheApplicationAvailableInRemoteRepoById() throws Exception {
    String applicationId = "102943";
    ApplicationResource application = applicationService.saveApplication(applicationId);

    Optional<Application> applicationInDatabase =
        applicationRepository.findApplicationByProjectId(applicationId);

    assertEquals(applicationId, applicationInDatabase.get().getProjectId());
    assertEquals(application.getEncodedPath(), applicationInDatabase.get().getEncodedPath());
    assertEquals(
        applicationInDatabase.get().getTag().getTagName(), application.getTag().getTagName());
  }

  @Test
  public void canSaveTheApplicationAvailableInRemoteRepoByProjectPathWithNamespace()
      throws Exception {
    String applicationId = "baris.bakla1/terraform";
    ApplicationResource application = applicationService.saveApplication(applicationId);

    Optional<Application> applicationInDatabase =
        applicationRepository.findApplicationByProjectId(application.getProjectId());

    assertEquals(application.getProjectId(), applicationInDatabase.get().getProjectId());
    assertEquals(application.getEncodedPath(), applicationInDatabase.get().getEncodedPath());
    assertEquals(
        application.getTag().getTagName(), applicationInDatabase.get().getTag().getTagName());
  }

  @Test
  public void returnsExceptionWhenApplicationBeingSavedIsNotFoundInRemoteRepo() throws Exception {
    String applicationId = "NoProject";
    assertThrows(
        ProjectNotFoundException.class, () -> applicationService.saveApplication(applicationId));
  }
}
