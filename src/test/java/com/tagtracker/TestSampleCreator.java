package com.tagtracker;

import com.tagtracker.model.entity.Project;

import com.tagtracker.model.entity.Tag;
import java.util.Date;

public class TestSampleCreator {

  public static Project createApplicationWithNoDependencies(boolean withTag) throws Exception {
    Project project = new Project();
    project.setProjectName("SampleApplication");
    project.setProjectId("testId");
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
}
