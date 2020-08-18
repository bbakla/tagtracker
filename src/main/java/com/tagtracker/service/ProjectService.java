package com.tagtracker.service;

import com.tagtracker.model.entity.gitlab.GitlabFile;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Jobs;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.StageSequenceResource;
import com.tagtracker.repository.ProjectRepository;
import com.tagtracker.repository.TagRepository;
import com.tagtracker.service.gitlab.GitlabService;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

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

    GitlabProject gitlabProject = gitService.getProjectFromGitlab(appIdentifier);

    if (gitlabProject == null) {
      throw new ProjectNotFoundException(
          String.format(
              "Be ure that project with the identifier %s is saved in this service",
              appIdentifier));
    }

    Project projectEntity = conversionService.convert(gitlabProject, Project.class);
    saveTagsIntoProject(projectEntity, projectEntity.getRemoteProjectId());

    putJobsToTheProject(gitlabProject.getId(), projectEntity.getTags());

    Project savedProject = projectRepository.save(projectEntity);

    return conversionService.convert(savedProject, ProjectResource.class);
  }

  public List<String> readStageOrder(String projectId) {
    GitlabFile file = gitService.readFileInRepository(projectId, ".gitlab-ci.yml");
    String ymlFile = new String(Base64.getDecoder().decode(file.getContent()));

    Yaml yaml = new Yaml();
    Reader reader = new StringReader(ymlFile);
    Map<String, Object> yamlFields = yaml.load(reader);

    return (List<String>) yamlFields.get("stages");

    /*List<StageSequenceResource> stageSequence = IntStream.range(0, stages.size())
        .mapToObj(i -> new StageSequenceResource(i, stages.get(i))).collect(Collectors.toList());*/

    //return stageSequence;
  }

  private void saveTagsIntoProject(Project projectEntity, String remoteProjectId) {
    Set<Tag> projectTags = getTagsOfRemoteRepository(remoteProjectId);
    if (projectTags != null) {
      projectTags.forEach(t -> t.setProject(projectEntity));
      projectEntity.setTags(projectTags);
    }
  }

  private void putJobsToTheProject(String repositoryId, Set<Tag> tags) {
    Map<JobKey, Set<Job>> tagJobs = getTagJobs(repositoryId);

    List<String> stageSequence = readStageOrder(repositoryId);
    System.out.println(stageSequence);

    tagJobs.entrySet().stream()
        .forEach(
            e -> {
              Optional<Tag> optionalTag =
                  tags.stream()
                      .filter(t -> t.getTagName().equals(e.getKey().getTagName()))
                      .findFirst();
              Tag tag = optionalTag.get();

              Jobs jobs = tag.getStages().get(e.getKey().getStage());
              if (jobs == null) {
                Jobs newJobs = new Jobs();
                newJobs.setStage(e.getKey().getStage());
                newJobs.setJobs(e.getValue());
                newJobs.setPipelineId(e.getKey().getPipelineId());
                newJobs.setStageOrder(stageSequence.indexOf(newJobs.getStage()));

                tag.addNewStage(newJobs);
              } else {
                tag.addJobToStage(e.getValue());
              }
            });


  }

  public Map<JobKey, Set<Job>> getTagJobs(String projectId) {

    GitlabJob[] gitlabJobs = gitService.getProjectJobs(projectId);
    Map<JobKey, Set<Job>> jobMap = new HashMap<>();

    Arrays.stream(gitlabJobs)
        .filter(job -> job.getTag())
        .forEach(
            gitlabJob -> {
              Job job = conversionService.convert(gitlabJob, Job.class);
              JobKey key =
                  new JobKey(
                      gitlabJob.getRef(),
                      gitlabJob.getStage(),
                      String.valueOf(gitlabJob.getPipeline().getId()));
              Set<Job> jobs = jobMap.get(gitlabJob.getRef());

              if (jobs != null) {
                jobs.add(job);
              } else {
                jobMap.put(key, Stream.of(job).collect(Collectors.toSet()));
              }
            });

    return jobMap;
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
    Optional<Project> applicationByProjectId =
        projectRepository.findProjectByRemoteProjectId(identifier);
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

    List<ProjectResource> projectsResources =
        projects.stream()
            .map(project -> conversionService.convert(project, ProjectResource.class))
            .collect(Collectors.toList());

    return projectsResources;
  }

  public void throwProjectNotFoundIfProjectNotAvailable(
      Optional<Project> project, String projectIdentifier) throws ProjectNotFoundException {
    if (project.isEmpty()) {
      throw new ProjectNotFoundException(
          String.format(
              "Be sure that you saved the project with projectIdentifier, %s into this service. Try first saving it before calling. ",
              projectIdentifier));
    }
  }
}

class JobKey {

  private String tagName;
  private String stage;
  private String pipelineId;

  public JobKey(String tagName, String stage, String pipelineId) {
    this.tagName = tagName;
    this.stage = stage;
    this.pipelineId = pipelineId;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public String getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(String pipelineId) {
    this.pipelineId = pipelineId;
  }

  @Override
  public int hashCode() {
    return (this.getTagName().hashCode()) + this.getStage().hashCode() + this.pipelineId.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof JobKey)) {
      return false;
    }

    JobKey jk = (JobKey) obj;
    return this.getStage().equals(jk.getStage())
        && this.getTagName().equals(jk.getTagName())
        && this.getPipelineId().equals(jk.getPipelineId());
  }
}
