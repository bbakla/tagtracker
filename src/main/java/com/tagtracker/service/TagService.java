package com.tagtracker.service;

import com.google.common.collect.Iterables;
import com.tagtracker.model.dto.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.GitlabTag;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private GitlabService gitlabService;

  @Autowired
  ConversionService conversionService;

  public TagResource addATagAsDependency(
      String projectIdentifier, String tagNameOfProject, DependencyDto dependentOnDto)
      throws ProjectNotFoundException {

    Project project = projectService.getProject(projectIdentifier);

    Optional<Project> relatedProject =
        projectRepository.findProjectByProjectName(dependentOnDto.getProjectName());

    projectService.throwProjectNotFoundIfProjectNotAvailable(
        relatedProject, dependentOnDto.getProjectName());

    Tag dependencyTag = relatedProject.get().findTag(dependentOnDto.getTagName());

    Tag dependentTag = project.findTag(tagNameOfProject);
    dependentTag.addDependency(dependencyTag);
    Tag dependentTagSaved = tagRepository.save(dependentTag);
    Tag relatedTagUpdated = tagRepository.save(dependencyTag);

    return conversionService.convert(dependentTagSaved, TagResource.class);
  }

  public TagResource addATagAsDependentOnMe(
      String projectIdentifier, String mainTagName, DependencyDto dependentOnMeDto)
      throws ProjectNotFoundException {

    Project mainProject = projectService.getProject(projectIdentifier);

    Optional<Project> dependentOnMeProjectOptional =
        projectRepository.findProjectByProjectName(dependentOnMeDto.getProjectName());
    projectService.throwProjectNotFoundIfProjectNotAvailable(
        dependentOnMeProjectOptional, dependentOnMeDto.getProjectName());

    Tag mainTag = mainProject.findTag(mainTagName);
    Tag dependentOnMeTag =
        dependentOnMeProjectOptional.get().findTag(dependentOnMeDto.getTagName());

    mainTag.addRelatedTag(dependentOnMeTag);
    Tag mainTagSaved = tagRepository.save(mainTag);

    dependentOnMeTag.addDependency(mainTag);
    tagRepository.save(dependentOnMeTag);

    return conversionService.convert(mainTagSaved, TagResource.class);
  }

  public TagResource deploy(String projectIdentifier, String tagName, Environment environment)
      throws ProjectNotFoundException {
    var project = projectService.getProject(projectIdentifier);
    Tag tag = project.findTag(tagName);
    tag.deployedTo(environment);

    Tag savedTag = tagRepository.save(tag);

    return conversionService.convert(savedTag, TagResource.class);
  }

  public TagResource createTag(String identifier, TagDto tagDto) throws ProjectNotFoundException {
    Project project = projectService.getProject(identifier);

    Project projectInDatabase = project;
    GitlabTag tagInRemote = gitlabService.createTag(projectInDatabase.getRemoteProjectId(), tagDto);

    Iterable<Tag> tags = tagRepository.findAll();

    Tag newTag = new Tag();
    newTag.setTagName(tagInRemote.getName());
    newTag.setMessage(tagInRemote.getMessage());
    newTag.setReleaseMessage(tagInRemote.getRelease().getDescription());
    newTag.setProject(projectInDatabase);

    projectInDatabase.addTag(newTag);
    Project projectWithNewTag = projectRepository.save(projectInDatabase);

    return conversionService.convert(
        projectWithNewTag.findTag(tagDto.getTagName()), TagResource.class);
  }

  @Transactional
  public void deleteTag(String identifier, String tagName, Boolean deleteRemoteTag)
      throws ProjectNotFoundException {
    Project project = projectService.getProject(identifier);

    if (project.findTag(tagName) == null) {
      throw new IllegalArgumentException(
          String.format("Application, %s, doesnt have a tag %s", identifier, tagName));
    }

    if (deleteRemoteTag) {
      gitlabService.deleteTag(identifier, tagName);
    }

    tagRepository.deleteByTagNameAndProject_RemoteProjectId(tagName, project.getRemoteProjectId());
    // project.removeTag(tagName);

    var tags = tagRepository.findAll();
    System.out.println(Iterables.size(tags));
  }

  public List<TagResource> getTagsOfAProject(String identifier) throws ProjectNotFoundException {
    Project project = projectService.getProject(identifier);

    List<TagResource> tags =
        project.getTags().stream()
            .map(t -> conversionService.convert(t, TagResource.class))
            .collect(Collectors.toList());

    return tags;
  }

  public TagResource getTagOfAProject(String identifier, String tagName)
      throws ProjectNotFoundException {
    Project project = projectService.getProject(identifier);

    TagResource tagResource = project.getTags()
        .stream()
        .filter(t -> t.getTagName().equals(tagName))
        .map(t -> conversionService.convert(t, TagResource.class))
        .findFirst().get();

    return tagResource;
  }

  private Tag addRelationToRelatedTag(Tag tagThatIsDependentOn, DependencyDto dependentOnDto,
      Tag hasNewDependentOnMe)
      throws ProjectNotFoundException {

    hasNewDependentOnMe.addRelatedTag(tagThatIsDependentOn);

    return tagRepository.save(hasNewDependentOnMe);
  }
}
