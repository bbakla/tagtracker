package com.tagtracker.service;

import com.tagtracker.model.dto.DependencyDto;
import com.tagtracker.model.dto.JobDto;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Jobs;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import com.tagtracker.model.entity.tracker.User;
import com.tagtracker.model.resource.JobResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.util.Arrays;
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

  public TagResource removeTagFromDependencies(
      String projectIdentifier, String tagNameOfProject, DependencyDto dependentOnDto)
      throws ProjectNotFoundException {

    Optional<Project> relatedProject =
        projectRepository.findProjectByProjectName(dependentOnDto.getProjectName());

    projectService.throwProjectNotFoundIfProjectNotAvailable(
        relatedProject, dependentOnDto.getProjectName());

    Tag relatedTag = relatedProject.get().findTag(dependentOnDto.getTagName());

    Project project = projectService.getProject(projectIdentifier);
    Tag projectTag = project.findTag(tagNameOfProject);
    projectTag.removeDependency(relatedTag);

    Tag dependentTagSaved = tagRepository.save(projectTag);
    Tag relatedTagUpdated = tagRepository.save(relatedTag);

    return conversionService.convert(dependentTagSaved, TagResource.class);
  }

  public TagResource runJob(String projectIdentifier, String tagName, String jobId, JobDto job)
      throws ProjectNotFoundException {
    var project = projectService.getProject(projectIdentifier);
    Tag tag = project.findTag(tagName);
    Jobs stageJobs = tag.getStages().get(job.getStage());

    if (stageJobs == null) {
      throw new IllegalArgumentException(
          String.format("The stage %s is not found", job.getStage()));
    }

    GitlabJob gitlabJob = gitlabService
        .playAJob(project.getRemoteProjectId(), jobId, job.getJobOperation());
    Optional<Job> playedJob = stageJobs.getJobs().stream()
        .filter(j -> j.getName().equals(gitlabJob.getName())).findAny();

    if (playedJob.isEmpty()) {
      throw new IllegalArgumentException(
          String.format("The JOB %s is not found", gitlabJob.getName()));
    }


    Job jobToBeUpdated = playedJob.get();
    stageJobs.removeJob(jobToBeUpdated.getJobId());

    jobToBeUpdated.setUser(conversionService.convert(gitlabJob.getUser(), User.class));
    jobToBeUpdated.setJobId(gitlabJob.getId());
    jobToBeUpdated.setStatus(gitlabJob.getStatus());

    stageJobs.addJob(jobToBeUpdated);

    Tag savedTag = tagRepository.save(tag);

    return conversionService.convert(savedTag, TagResource.class);
  }

  public JobResource getTagJob(String projectId, String tagName, String jobId) {

    GitlabJob gitlabJob = gitlabService.getProjectJob(projectId, jobId);
    return conversionService.convert(gitlabJob, JobResource.class);
  }


  public TagResource createTag(String identifier, TagDto tagDto) throws ProjectNotFoundException {
    Project project = projectService.getProject(identifier);

    Project projectInDatabase = project;
    GitlabTag tagInRemote = gitlabService.createTag(projectInDatabase.getRemoteProjectId(), tagDto);

    Iterable<Tag> tags = tagRepository.findAll();

    Tag newTag = new Tag();
    newTag.setTagName(tagInRemote.getName());
    newTag.setMessage(tagInRemote.getMessage());
    newTag.setReleaseMessage(tagInRemote.getRelease() != null ? tagInRemote.getRelease().getDescription() : null);
    newTag.setProject(projectInDatabase);

    projectService.putJobsIntoTheTag(identifier, newTag);

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
}
