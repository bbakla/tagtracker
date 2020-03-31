package com.tagtracker;

import com.tagtracker.model.entity.Application;

import com.tagtracker.model.entity.Tag;
import java.util.Date;

public class TestSampleCreator {

  public static Application createApplication() throws Exception {
    Application application = new Application();
    application.setApplicationName("SampleApplication");
    application.setProjectId("testId");
    application.setEncodedPath("baris.bakla1/terraform");

    application.setTag(createTag(application));

    return application;
  }

  public static Tag createTag(Application application) {
    Tag tag = new Tag();
    tag.setCreatedDate(new Date());
//    tag.setId(application.getProjectId());
    tag.setMessage("tag message");
    tag.setTagName("tagName");
    tag.setApplication(application);

    return tag;
  }
}
