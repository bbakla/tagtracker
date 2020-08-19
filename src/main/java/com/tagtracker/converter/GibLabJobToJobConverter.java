package com.tagtracker.converter;

import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.tracker.User;
import org.springframework.core.convert.converter.Converter;

public class GibLabJobToJobConverter implements Converter<GitlabJob, Job> {

  @Override
  public Job convert(GitlabJob source) {
    User user = new User();
    user.setFullName(source.getUser().getName());
    user.setUserId(source.getUser().getId());
    user.setUserName(source.getUser().getUsername());

    Job job = new Job();
    job.setStage(source.getStage());
    job.setName(source.getName());
    job.setJobId(source.getId());
    job.setPipelineStatus(source.getPipeline().getStatus());
    job.setUser(user);
    job.setStatus(source.getStatus());

    return job;
  }
}
