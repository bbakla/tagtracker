package com.tagtracker.service;

import com.tagtracker.controller.DependencyDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.Environment;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.GitlabTag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.util.List;
import java.util.Optional;
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
      String projectIdentifier, String mainTagName, DependencyDto dependentOnDto)
      throws ProjectNotFoundException {

    Project project = projectService.getProject(projectIdentifier);

    Optional<Project> dependentOnProjectOptional =
        projectRepository.findProjectByProjectName(dependentOnDto.getProjectName());
    projectService.throwProjectNotFoundIfProjectNotAvailable(
        dependentOnProjectOptional, dependentOnDto.getProjectName());

    Tag tagThatIsDependentOn = project.findTag(mainTagName);
    Tag savedDependentOnThatTag = addDependencyOnMeForATag(tagThatIsDependentOn, dependentOnDto);

    tagThatIsDependentOn.addDependency(savedDependentOnThatTag);
    Tag savedTagThatIsDependentOn = tagRepository.save(tagThatIsDependentOn);

    return conversionService.convert(savedTagThatIsDependentOn, TagResource.class);
  }

  public TagResource addATagAsDependentOnMe(String projectIdentifier, String mainTagName,
      DependencyDto dependentOnMeDto) throws ProjectNotFoundException {

    Project mainProject = projectService.getProject(projectIdentifier);

    Optional<Project> dependentOnMeProjectOptional =
        projectRepository.findProjectByProjectName(dependentOnMeDto.getProjectName());
    projectService.throwProjectNotFoundIfProjectNotAvailable(
        dependentOnMeProjectOptional, dependentOnMeDto.getProjectName());

    Tag mainTag = mainProject.findTag(mainTagName);
    Tag dependentOnMeTag = dependentOnMeProjectOptional.get()
        .findTag(dependentOnMeDto.getTagName());

    mainTag.addDependentOnMe(dependentOnMeTag);
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
    GitlabTag tagInRemote = gitlabService.createTag(projectInDatabase.getProjectId(), tagDto);

    List<Tag> tags = tagRepository.findAll();

    Tag newTag = new Tag();
    newTag.setTagName(tagInRemote.getName());
    newTag.setMessage(tagInRemote.getMessage());
    newTag.setReleaseMessage(tagInRemote.getRelease().getDescription());
    newTag.setProject(projectInDatabase);

    projectInDatabase.addTag(newTag);
    Project projectWithNewTag = projectRepository.save(projectInDatabase);

    return conversionService
        .convert(projectWithNewTag.findTag(tagDto.getTagName()), TagResource.class);
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

    tagRepository.deleteTagByTagNameAndProjectProjectId(tagName, project.getProjectId());
    //project.removeTag(tagName);

  }


  private Tag addDependencyOnMeForATag(Tag tagThatIsDependentOn, DependencyDto dependentOnDto) {

    Tag tagDependentOnMe =
        tagRepository.findTagByTagNameAndProjectProjectName(
            dependentOnDto.getTagName(), dependentOnDto.getProjectName());
    tagDependentOnMe.addDependentOnMe(tagThatIsDependentOn);

    return tagRepository.save(tagDependentOnMe);
  }

}
