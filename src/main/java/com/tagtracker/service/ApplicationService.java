package com.tagtracker.service;

import com.tagtracker.controller.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.repository.ProjectRepository;
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
  private ProjectRepository projectRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private GitlabService gitService;

  @Autowired
  ConversionService conversionService;

  public ApplicationResource saveApplication(String appIdentifier) throws ProjectNotFoundException {

    com.tagtracker.model.entity.gitlab.Project project = gitService
        .getProjectFromGitlab(appIdentifier);

    if (project == null) {
      throw new ProjectNotFoundException(String
          .format("Be ure that project with the identifier %s is saved in this service",
              appIdentifier));
    }
    Project application = new Project();
    application.setProjectId(project.getId());
    application.setProjectName(project.getName());
    application.setEncodedPath(project.getPath_with_namespace());

    Tag applicationTag = getTag(application.getProjectId());
    // applicationTag.(project.getId());

    if (applicationTag != null) {
      applicationTag.setProject(application);
      application.addTag(applicationTag);
    }

    Project savedProject = projectRepository.save(application);

    return conversionService.convert(savedProject, ApplicationResource.class);
  }

  public ApplicationResource getByProjectIdOrPath(String identifier)
      throws ProjectNotFoundException {
    Optional<Project> application = getApplicationByProjectIdOrPath(identifier);

    if (application.isEmpty()) {
      throw new ProjectNotFoundException(String
          .format("Project with the identifier %s is not found in the repository", identifier));
    }

    return conversionService.convert(application.get(), ApplicationResource.class);
  }

  private Optional<Project> getApplicationByProjectIdOrPath(String identifier) {
    Optional<Project> applicationByProjectId =
        projectRepository.findProjectByProjectId(identifier);
    if (applicationByProjectId.isEmpty()) {
      return projectRepository.findProjectByEncodedPath(identifier);
    }

    return applicationByProjectId;
  }

  public Tag getLatestTagOfApplication(String id) throws ProjectNotFoundException {
    Optional<Project> application = projectRepository.findProjectByProjectId(id);
    if (application.isEmpty()) {
      throw new ProjectNotFoundException(
          "Project information is not saved in the application repository. Try first saving it before calling.");
    }

    Tag latestTag =
        application.get().getTags().stream()
            .sorted(Comparator.comparing(Tag::getCreatedDate))
            .collect(Collectors.toList())
            .get(0);

    return latestTag;
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

  public ApplicationResource addDependency(String identifier, String tagName,
      DependencyDto dependentTo)
      throws ProjectNotFoundException {

    Optional<Project> application = getApplicationByProjectIdOrPath(identifier);
    if (application.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Be sure that you saved the project with identifier, %s into this service. Try first saving it before calling. ",
              identifier));
    }

    Optional<Project> dependentToApplicationOptional = projectRepository.findProjectByProjectName(
        dependentTo.getProjectName());
    if (dependentToApplicationOptional.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Be sure that you saved the project with identifier, %s into this service. Try first saving it before calling. ",
              dependentTo));
    }
    Project dependentToProject = dependentToApplicationOptional.get();

//    dependentToApplication.findTag(dependentTo.getTagName()).addDependencyToMe(application.get());
    projectRepository.save(dependentToProject);

    //  application.get().findTag(tagName).addDependency(dependentToApplication);
    Project savedProject = projectRepository.save(application.get());

    return conversionService.convert(savedProject, ApplicationResource.class);
  }

  public ApplicationResource deploy(String identifier, Environment environment)
      throws ProjectNotFoundException {
    Optional<Project> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Project project = applicationFound.get();
    //application.deployedTo(environment);
    ;

    Project savedProject = projectRepository.save(project);

    return conversionService.convert(savedProject, ApplicationResource.class);
  }

  public void deleteApp(String identifier) throws ProjectNotFoundException {
    Optional<Project> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    projectRepository.deleteByProjectId(applicationFound.get().getProjectId());
  }

  public void deleteTag(String identifier, String tagName, Boolean deleteRemoteTag)
      throws ProjectNotFoundException {
    Optional<Project> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    if (applicationFound.get().findTag(tagName) == null) {
      throw new IllegalArgumentException(
          String.format("Application, %s, doesnt have a tag %s", identifier, tagName));
    }

    //applicationRepository.deleteByProjectIdAndTagTagName(identifier, tagName);
    if (deleteRemoteTag) {
      gitService.deleteTag(identifier, tagName);
    }
  }

  public Tag createTag(String identifier, TagDto tagDto) throws ProjectNotFoundException {
    Optional<Project> applicationFound = getApplicationByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Project projectInDatabase = applicationFound.get();
    RemoteTag tagInRemote = gitService.createTag(projectInDatabase.getProjectId(), tagDto);

    List<Tag> tags = tagRepository.findAll();

    Tag newTag = new Tag();
    newTag.setTagName(tagInRemote.getName());
    newTag.setMessage(tagInRemote.getMessage());
    newTag.setReleaseMessage(tagInRemote.getRelease().getDescription());

    projectInDatabase.addTag(newTag);
    newTag.setProject(projectInDatabase);
    Tag savedTag = tagRepository.save(newTag);

    projectInDatabase.addTag(savedTag);

    return projectRepository.save(projectInDatabase).findTag(savedTag.getTagName());

  }
}
