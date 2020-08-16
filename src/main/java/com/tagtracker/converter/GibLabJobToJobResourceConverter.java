package com.tagtracker.converter;

import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.User;
import com.tagtracker.model.resource.JobResource;
import com.tagtracker.model.resource.UserResource;
import org.springframework.core.convert.converter.Converter;

public class GibLabJobToJobResourceConverter implements Converter<GitlabJob, JobResource> {

  @Override
  public JobResource convert(GitlabJob source) {
    JobResource jobResource = new JobResource();
    jobResource.setName(source.getName());
    jobResource.setJobId(source.getId());
    jobResource.setPipelineStatus(source.getPipeline().getStatus());

    UserResource user = new UserResource();
    user.setName(source.getUser().getName());
    user.setUserId(source.getUser().getId());
    user.setUserName(source.getUser().getUsername());

    jobResource.setUserResource(user);

    return jobResource;
  }
}
