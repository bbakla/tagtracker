package com.tagtracker.service;

import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  public ProjectResource saveRemoteProjectRepositoryInformation(String appIdentifier)
      throws ProjectNotFoundException {

    GitlabProject project = gitService.getProjectFromGitlab(appIdentifier);

    if (project == null) {
      throw new ProjectNotFoundException(
          String.format(
              "Be ure that project with the identifier %s is saved in this service",
              appIdentifier));
    }
    Project projectEntity = new Project();
    projectEntity.setRemoteProjectId(project.getId());
    projectEntity.setProjectName(project.getName());
    projectEntity.setEncodedPath(project.getPathWithNamespace());
    projectEntity.setDescription(project.getDescription());

    Set<Tag> projectTags = getTagsOfRemoteRepository(projectEntity.getRemoteProjectId());
    if (projectTags != null) {
      projectTags.forEach(t -> t.setProject(projectEntity));
      projectEntity.setTags(projectTags);
    }



    Project savedProject = projectRepository.save(projectEntity);

    return conversionService.convert(savedProject, ProjectResource.class);
  }

  public ProjectResource getByProjectIdOrPath(String identifier) throws ProjectNotFoundException {
    Optional<Project> project = getProjectByProjectIdOrPath(identifier);

    if (project.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with the identifier %s is not found in the repository", identifier));
    }

    return conversionService.convert(project.get(), ProjectResource.class);
  }

  public Optional<Project> getProjectByProjectIdOrPath(String identifier) {
    Optional<Project> applicationByProjectId = projectRepository
        .findProjectByRemoteProjectId(identifier);
    if (applicationByProjectId.isEmpty()) {
      return projectRepository.findProjectByEncodedPath(identifier);
    }

    return applicationByProjectId;
  }

  public void deleteProject(String identifier) throws ProjectNotFoundException {
    Optional<Project> projectFound = getProjectByProjectIdOrPath(identifier);
    if (projectFound.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Project with identifier, %s is not saved in the application repository. Try first saving it before calling. ",
              identifier));
    }

    projectRepository.deleteByRemoteProjectId(projectFound.get().getRemoteProjectId());
  }

  public Set<Tag> getTagsOfRemoteRepository(String projectId) {
    GitlabTag[] tags = gitService.getTagsOfARemoteRepository(projectId);

    if (tags.length == 0) {
      return null;
    }

    return Arrays.stream(tags)
        .map(
            gitlabTag -> {
              Tag tag = conversionService.convert(gitlabTag, Tag.class);

              return tag;
            })
        // .sorted(Comparator.comparing(GitlabTag::getCommitDate))
        .collect(Collectors.toSet());
  }

  public Project getProject(String projectIdentifier) throws ProjectNotFoundException {
    Optional<Project> project = getProjectByProjectIdOrPath(projectIdentifier);
    throwProjectNotFoundIfProjectNotAvailable(project, projectIdentifier);

    return project.get();
  }

  public List<ProjectResource> getProjects() {
    List<Project> projects = projectRepository.findAll();

    List <ProjectResource> projectsResources = projects.stream().
        map(project -> conversionService.convert(project, ProjectResource.class)).
        collect(Collectors.toList());

    return projectsResources;
  }

  public void throwProjectNotFoundIfProjectNotAvailable(Optional<Project> project,
      String projectIdentifier)
      throws ProjectNotFoundException {
    if (project.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Be sure that you saved the project with projectIdentifier, %s into this service. Try first saving it before calling. ",
              projectIdentifier));
    }
  }
}
