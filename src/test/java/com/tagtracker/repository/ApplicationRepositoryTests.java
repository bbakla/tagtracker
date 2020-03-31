package com.tagtracker.repository;

import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.entity.Application;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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
public class ApplicationRepositoryTests {

  @Autowired
  private ApplicationRepository applicationRepository;

  @AfterEach
  public void cleanUp() {
    applicationRepository.deleteAll();
  }

  @Test
  public void canSaveAnApplication() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    assertNotNull(savedApplication.getProjectId());
    assertEquals(application.getApplicationName(), savedApplication.getApplicationName());
    assertNotNull(savedApplication.getCreatedDate());
    assertNotNull(savedApplication.getLastModifiedDate());
    assertNotNull(application.getTag().getId());
    assertEquals(application.getTag(), savedApplication.getTag());
  }

  @Test
  public void cannotCreateATagWithTheSameNameToAnApplication() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    assertEquals(application.getApplicationName(), savedApplication.getApplicationName());
    assertEquals(application.getTag(), savedApplication.getTag());

    Application application1 = TestSampleCreator.createApplication();
    assertThrows(
        DataIntegrityViolationException.class, () -> applicationRepository.save(application1));
  }

  @Test
  public void cannotCreateAnApplicationWithTheSameName() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    assertEquals(application.getApplicationName(), savedApplication.getApplicationName());
    assertEquals(application.getTag(), savedApplication.getTag());

    Application application1 =
        new Application(application.getProjectId(), application.getApplicationName());
    assertThrows(
        DataIntegrityViolationException.class, () -> applicationRepository.save(application1));
  }

  @Test
  public void canGetAnApplicationFromDatabaseById() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Optional<Application> applicationInDatabase =
        applicationRepository.findApplicationByProjectId(savedApplication.getProjectId());

    assertNotNull(applicationInDatabase.get().getProjectId());
    assertEquals(
        application.getApplicationName(), applicationInDatabase.get().getApplicationName());
    assertEquals(application.getProjectId(), applicationInDatabase.get().getProjectId());
    assertNotNull(application.getTag().getId());
    assertEquals(application.getTag(), applicationInDatabase.get().getTag());
  }

  @Test
  @Transactional
  public void canGetAnApplicationFromDatabaseById2() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Optional<Application> applicationInDatabase =
        applicationRepository.findApplicationByProjectId(savedApplication.getProjectId());

    assertNotNull(applicationInDatabase.get().getProjectId());
    assertEquals(application.getApplicationName(),
        applicationInDatabase.get().getApplicationName());
    assertEquals(application.getProjectId(), applicationInDatabase.get().getProjectId());
    assertNotNull(application.getTag().getId());
    assertEquals(application.getTag(), applicationInDatabase.get().getTag());
  }

  @Test
  public void canGetAnApplicationByTagAndId() throws Exception {
    Application application = TestSampleCreator.createApplication();
    applicationRepository.save(application);

    Application application2 = TestSampleCreator.createApplication();
    application2.setProjectId("secondAppId");
    application2.setApplicationName("secondApplicationName");
    //application2.getTag().setId(application2.getProjectId());

    Application savedApplication2 = applicationRepository.save(application2);

    Application getApplicationByIdandTag =
        applicationRepository.findApplicationByProjectIdAndTagTagName(
            savedApplication2.getProjectId(), savedApplication2.getTag().getTagName());

    assertNotNull(getApplicationByIdandTag.getProjectId());
    assertEquals(application2.getApplicationName(), getApplicationByIdandTag.getApplicationName());
    assertEquals(application2.getTag(), getApplicationByIdandTag.getTag());
  }

  @Test
  public void canGetTagUsingProjectId() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    assertEquals(application.getProjectId(), savedApplication.getTag().getProjectId());
  }

  @Test
  public void canFindAProjectUsingTagNameAndProjectId() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);
    Application applicationFoundByTagName = applicationRepository
        .findApplicationByProjectIdAndTagTagName(application.getProjectId(),
            application.getTag().getTagName());
    assertEquals(application.getTag().getTagName(),
        applicationFoundByTagName.getTag().getTagName());

    String secondTagName = "secondTag";
    Application application2 = TestSampleCreator.createApplication();
    application2.getTag().setTagName(secondTagName);

    Application savedApplicationWithTag = applicationRepository.save(application2);
    Application applicationWithSecondTag = applicationRepository
        .findApplicationByProjectIdAndTagTagName(application.getProjectId(), secondTagName);
    assertEquals(secondTagName, applicationWithSecondTag.getTag().getTagName());

    List<Application> applicationList = applicationRepository.findAll();
    assertEquals(2, applicationList.size());
  }

  @Test
  //@Transactional
  public void canDeleteAProjectUsingProjectId() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);
    Application applicationFoundByTagName = applicationRepository
        .findApplicationByProjectIdAndTagTagName(application.getProjectId(),
            application.getTag().getTagName());
    assertEquals(application.getTag().getTagName(),
        applicationFoundByTagName.getTag().getTagName());

    String secondTagName = "secondTag";
    Application application2 = TestSampleCreator.createApplication();
    application2.getTag().setTagName(secondTagName);
    Application savedApplicationWithTag = applicationRepository.save(application2);
    List<Application> applicationList = applicationRepository.findAll();
    assertEquals(2, applicationList.size());

    applicationRepository.deleteByProjectId(savedApplication.getProjectId());

    assertEquals(0, applicationRepository.findAll().size());
  }

  @Test
  //@Transactional
  public void canDeleteTagOfAProject() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    String secondTagName = "secondTag";
    Application application2 = TestSampleCreator.createApplication();
    application2.getTag().setTagName(secondTagName);
    Application savedApplicationWithTag = applicationRepository.save(application2);

    applicationRepository.deleteByProjectIdAndTagTagName(savedApplicationWithTag.getProjectId(),
        savedApplicationWithTag.getTag().getTagName());

    assertEquals(1, applicationRepository.findAll().size());
  }

  @Test
  public void canSetDependenciesWithOtherServices() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Application application2 = TestSampleCreator.createApplication();
    application2.setProjectId("Independent");
    application2.setApplicationName("independentProject");
    application2.setEncodedPath("/independent");
    application2.getTag().setTagName("independentTag");
    Application savedApplication2 = applicationRepository.save(application2);

    application.addDependency(application2);
    application2.addDependencyToMe(application);

    savedApplication = applicationRepository.save(application);
    savedApplication2 = applicationRepository.save(application2);

    assertEquals(savedApplication.getDependentTo().size(),
        savedApplication2.getDependentOnMe().size());

    Application finalSavedApplication = savedApplication2;
    assertTrue(savedApplication.getDependentTo().stream().anyMatch(a -> a.getProjectId().equals(
        finalSavedApplication.getProjectId())));
  }

  @Test
  public void canBeDependentOnDifferentTagsOfTheSameApplication() throws Exception {
    Application application = TestSampleCreator.createApplication();
    Application savedApplication = applicationRepository.save(application);

    Application application2 = TestSampleCreator.createApplication();
    application2.setProjectId("Independent");
    application2.setApplicationName("independentProject");
    application2.setEncodedPath("/independent");
    application2.getTag().setTagName("independentTag");
    Application savedApplication2 = applicationRepository.save(application2);

    application.addDependency(application2);
    application2.addDependencyToMe(application);
    savedApplication = applicationRepository.save(application);
    savedApplication2 = applicationRepository.save(application2);

    //New tag
    Application application3 = new Application();
    application3.setProjectId(application2.getProjectId());
    application3.setApplicationName(application2.getApplicationName());
    application3.setEncodedPath(application2.getEncodedPath());
    application3.setTag(TestSampleCreator.createTag(application3));
    application3.getTag().setTagName("independentTag2");
    application3.getTag().setMessage("message");

    Application savedApplication2NewTag = applicationRepository.save(application3);

    application.removeDependency(application2);
    application2.removeDependentApplication(application);
    application.addDependency(application3);
    application3.addDependencyToMe(application);

    savedApplication = applicationRepository.save(application);
    savedApplication2 = applicationRepository.save(application2);
    savedApplication2NewTag = applicationRepository.save(application3);

    Application savedApplicationDependsOn = savedApplication.getDependentTo().stream().findFirst()
        .get();
    assertEquals(savedApplication2NewTag.getTag().getTagName(),
        savedApplicationDependsOn.getTag().getTagName());
  }

  @Test
  public void canDeployToAnEnvironment() throws Exception {
    Application application = TestSampleCreator.createApplication();
    applicationRepository.save(application);

    application.deployedTo(Environment.DEV);

    Application deployedApplication = applicationRepository.save(application);
    Optional<Application> savedApplication = applicationRepository
        .findApplicationByProjectId(application.getProjectId());

    assertTrue(savedApplication.get().getDeployedEnvironments().size() == 1);
  }

}
