package com.tagtracker.converter;

import com.sun.source.tree.Tree;
import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Jobs;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.resource.JobResource;
import com.tagtracker.model.resource.JobsResource;
import com.tagtracker.model.resource.TagResource;
import com.tagtracker.model.resource.UserResource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

public class TagToTagResourceConverter implements Converter<Tag, TagResource> {

  @Override
  public TagResource convert(Tag source) {
    var tagResource =
        new TagResource(source.getRemoteProjectId(), source.getProjectName(), source.getTagName(),
            source.getMessage(),
            source.getReleaseMessage());

    tagResource.setStages(convertJobs(source.getStages()));

    tagResource.setTagsDependentOn(convertDependencies(source.getDependentOn()));
    tagResource.setTagsDependentOnMe(convertDependencies(source.getRelatedTags()));

    return tagResource;
  }

  private Map<String, JobsResource> convertJobs(Map<String, Jobs> jobs) {
    Map<String, JobsResource> jobsList = new HashMap<>();

    jobs.entrySet().stream().forEach(e -> {
      Set<JobResource> jobLists = new HashSet<>();
      Jobs tagJobs = e.getValue();
      JobsResource jobsContainer = new JobsResource();
      //jobsContainer.setJobResources(jobLists);
      jobsContainer.setPipelineId(tagJobs.getPipelineId());
      jobsContainer.setStageIndex(tagJobs.getStageOrder());

      Set<JobResource> convertedToJobsResources = tagJobs.getJobs().stream().map(j -> {
        UserResource userResource = new UserResource();
        userResource.setName(j.getUser().getFullName());
        userResource.setUserId(j.getUser().getUserId());
        userResource.setUserName(j.getUser().getUserName());

        JobResource jobResource = new JobResource();
        jobResource.setUserResource(userResource);
        jobResource.setName(j.getName());
        //jobResource.setPipelineId(tagJobs.getPipelineId());
        //jobResource.setPipelineStatus(j.getPipelineStatus());
        jobResource.setStage(j.getStage());
        jobResource.setStatus(j.getStatus());
        jobResource.setJobId(j.getJobId());

        jobsContainer.setPipelineStatus(j.getPipelineStatus());
        jobsContainer.setStage(j.getStage());

        return jobResource;


      }).collect(Collectors.toSet());

      jobsContainer.setJobResources(convertedToJobsResources);
      jobsList.put(e.getKey(), jobsContainer);

    });

    Map<String, JobsResource> result = jobsList.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    return result;

    //return new TreeMap<>(jobsList);
  }

  private Set<TagResource> convertDependencies(Set<Tag> dependencies) {
    return dependencies.stream()
        .map(d -> new TagResource(d.getRemoteProjectId(), d.getProjectName(), d.getTagName(),
            d.getMessage(),
            d.getReleaseMessage()))
        .collect(Collectors.toSet());
  }
}
