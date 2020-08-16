package com.tagtracker;

import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.Project;

import com.tagtracker.model.entity.tracker.Tag;
import java.util.Date;

public class TestSampleCreator {

  public static Project createAProjectWithNoDependencies(boolean withTag) {
    Project project = new Project();
    project.setProjectName("SampleApplication");
    project.setRemoteProjectId("testId");
    project.setEncodedPath("baris.bakla1/terraform");

    if (withTag) {
      project.addTag(createTag("tagName", project));
    }

    return project;
  }

  public static Tag createTag(String tagName, Project project) {
    Tag tag = new Tag();
    tag.setCreatedDate(new Date());
    tag.setMessage("tag message");
    tag.setTagName(tagName);
    tag.setProject(project);

    return tag;
  }

  public static Job createJob(String stage) {
    Job job = new Job();
    job.setJobId("111");
    job.setName("name_" + stage);
    job.setStage(stage);

    return job;

  }
}
