package com.tagtracker.service;

import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Application;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.Project;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.repository.ApplicationRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private GitlabService gitService;

  @Autowired
  ConversionService conversionService;

  public ApplicationResource saveApplication(String appIdentifier) throws ProjectNotFoundException {

    Project project = gitService.getProjectFromGitlab(appIdentifier);

    if (project == null) {
      throw new ProjectNotFoundException(String
          .format("Project with the identifier %s is not found in the repository", appIdentifier));
    }
    Application application = new Application();
    application.setProjectId(project.getId());
    application.setApplicationName(project.getName());
    application.setEncodedPath(project.getPath_with_namespace());

    Tag applicationTag = getTag(application.getProjectId());
    // applicationTag.(project.getId());

    if (applicationTag != null) {
      applicationTag.setApplication(application);
      application.setTag(applicationTag);
    }

    Application savedApplication = applicationRepository.save(application);

    return conversionService.convert(savedApplication, ApplicationResource.class);
  }

  public ApplicationResource getByProjectIdOrPath(String identifier)
      throws ProjectNotFoundException {
    Optional<Application> application = getApplicationByProjectIdOrPath(identifier);

    if (application.isEmpty()) {
      throw new ProjectNotFoundException(String
          .format("Project with the identifier %s is not found in the repository", identifier));
    }

    return conversionService.convert(application.get(), ApplicationResource.class);
  }

  private Optional<Application> getApplicationByProjectIdOrPath(String identifier) {
    Optional<Application> applicationByProjectId =
        applicationRepository.findApplicationByProjectId(identifier);
    if (applicationByProjectId.isEmpty()) {
      return applicationRepository.findApplicationByEncodedPath(identifier);
    }

    return applicationByProjectId;
  }

  public Tag getLatestTagOfApplication(String id) throws ProjectNotFoundException {
    Optional<Application> application = applicationRepository.findApplicationByProjectId(id);
    if (application.isEmpty()) {
      throw new ProjectNotFoundException(
          "Project information is not saved in the application repository. Try first saving it before calling.");
    }

    return application.get().getTag();
  }

  private Tag getTag(String appId) {
    RemoteTag[] tags = gitService.getTagsOfProject(appId);

    if (tags.length == 0) {
      return null;
    }

    Tag applicationTag = new Tag();
    //    applicationTag.setId(appId);

    RemoteTag latestTag =
        Arrays.stream(tags)
            .sorted(Comparator.comparing(RemoteTag::getCommitDate))
            .collect(Collectors.toList())
            .get(0);

    applicationTag.setTagName(latestTag.getName());
    applicationTag.setMessage(latestTag.getMessage());

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

    try {
      Date creationDate = sdf.parse(latestTag.getCommitDate());
      applicationTag.setCreatedDate(creationDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return applicationTag;
  }

  public ApplicationResource addDependency(String identifier, String dependentTo)
      throws ProjectNotFoundException {

    Optional<Application> application = getApplicationByProjectIdOrPath(identifier);
    if (application.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Optional<Application> dependentToApplicationOptional = getApplicationByProjectIdOrPath(
        dependentTo);
    if (dependentToApplicationOptional.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              dependentTo));
    }

    Application dependentToApplication = applicationRepository
        .findApplicationByProjectId(dependentToApplicationOptional.get().getProjectId()).get();
    dependentToApplication.addDependencyToMe(application.get());
    applicationRepository.save(dependentToApplication);

    application.get().addDependency(dependentToApplication);
    Application savedApplication = applicationRepository.save(application.get());

    return conversionService.convert(savedApplication, ApplicationResource.class);
  }

  public ApplicationResource deploy(String identifier, Environment environment)
      throws ProjectNotFoundException {
    Optional<Application> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Application application = applicationFound.get();
    application.deployedTo(environment);
    ;

    Application savedApplication = applicationRepository.save(application);

    return conversionService.convert(savedApplication, ApplicationResource.class);
  }

  public void deleteApp(String identifier) throws ProjectNotFoundException {
    Optional<Application> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    applicationRepository.deleteByProjectId(applicationFound.get().getProjectId());
  }

  public void deleteTag(String identifier, String tagName, Boolean deleteRemoteTag)
      throws ProjectNotFoundException {
    Optional<Application> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    if (!applicationFound.get().getTag().getTagName().equals(tagName)) {
      throw new IllegalArgumentException(
          String.format("Application, %s, doesnt have a tag %s", identifier, tagName));
    }

    applicationRepository.deleteByProjectIdAndTagTagName(identifier, tagName);
    if (deleteRemoteTag) {
      gitService.deleteTag(identifier, tagName);
    }
  }

  public Tag createTag(String identifier, TagDto tagDto) throws ProjectNotFoundException {
    Optional<Application> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Application applicationInDatabase = applicationFound.get();
    RemoteTag tagInRemote = gitService.createTag(applicationInDatabase.getProjectId(), tagDto);

    List<Tag> tags = tagRepository.findAll();

    Tag newTag = new Tag();
    newTag.setTagName(tagInRemote.getName());
    newTag.setMessage(tagInRemote.getMessage());
    newTag.setReleaseMessage(tagInRemote.getRelease().getDescription());

    applicationInDatabase.setTag(newTag);
    newTag.setApplication(applicationInDatabase);
    Tag savedTag = tagRepository.save(newTag);

    applicationInDatabase.setTag(savedTag);

    return applicationRepository.save(applicationInDatabase).getTag();

  }
}
