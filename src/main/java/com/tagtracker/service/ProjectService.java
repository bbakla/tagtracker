package com.tagtracker.service;

import com.tagtracker.controller.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.GitlabTag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private GitlabService gitService;

  @Autowired
  ConversionService conversionService;

  public ProjectResource saveProject(String appIdentifier) throws ProjectNotFoundException {

    GitlabProject project = gitService
        .getProjectFromGitlab(appIdentifier);

    if (project == null) {
      throw new ProjectNotFoundException(String
          .format("Be ure that project with the identifier %s is saved in this service",
              appIdentifier));
    }
    Project projectEntity = new Project();
    projectEntity.setProjectId(project.getId());
    projectEntity.setProjectName(project.getName());
    projectEntity.setEncodedPath(project.getPath_with_namespace());

    Set<Tag> projectTags = getTag(projectEntity.getProjectId());

    projectTags.forEach(t -> t.setProject(projectEntity));
    projectEntity.setTags(projectTags);

    Project savedProject = projectRepository.save(projectEntity);

    return conversionService.convert(savedProject, ProjectResource.class);
  }

  public ProjectResource getByProjectIdOrPath(String identifier)
      throws ProjectNotFoundException {
    Optional<Project> project = getProjectByProjectIdOrPath(identifier);

    if (project.isEmpty()) {
      throw new ProjectNotFoundException(String
          .format("Project with the identifier %s is not found in the repository", identifier));
    }

    return conversionService.convert(project.get(), ProjectResource.class);
  }

  private Optional<Project> getProjectByProjectIdOrPath(String identifier) {
    Optional<Project> applicationByProjectId =
        projectRepository.findProjectByProjectId(identifier);
    if (applicationByProjectId.isEmpty()) {
      return projectRepository.findProjectByEncodedPath(identifier);
    }

    return applicationByProjectId;
  }

/*  public Tag getLatestTagOfApplication(String id) throws ProjectNotFoundException {
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
  }*/


  private Set<Tag> getTag(String projectId) {
    GitlabTag[] tags = gitService.getTagsOfAProject(projectId);

    if (tags.length == 0) {
      return null;
    }

    return
        Arrays.stream(tags)
            .map(gitlabTag -> {
              Tag tag = conversionService.convert(gitlabTag, Tag.class);

              return tag;
            })
            //.sorted(Comparator.comparing(GitlabTag::getCommitDate))
            .collect(Collectors.toSet());

  }

  public ProjectResource addDependency(String identifier, String tagName,
      DependencyDto dependentTo)
      throws ProjectNotFoundException {

    Optional<Project> application = getProjectByProjectIdOrPath(identifier);
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

    return conversionService.convert(savedProject, ProjectResource.class);
  }

  public ProjectResource deploy(String identifier, Environment environment)
      throws ProjectNotFoundException {
    Optional<Project> applicationFound = getProjectByProjectIdOrPath(identifier);
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

    return conversionService.convert(savedProject, ProjectResource.class);
  }

  public void deleteApp(String identifier) throws ProjectNotFoundException {
    Optional<Project> applicationFound = getProjectByProjectIdOrPath(identifier);
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
    Optional<Project> applicationFound = getProjectByProjectIdOrPath(identifier);
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
    Optional<Project> applicationFound = getProjectByProjectIdOrPath(identifier);
    if (applicationFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    Project projectInDatabase = applicationFound.get();
    GitlabTag tagInRemote = gitService.createTag(projectInDatabase.getProjectId(), tagDto);

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
