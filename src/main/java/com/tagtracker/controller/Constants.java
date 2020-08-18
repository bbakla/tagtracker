package com.tagtracker.controller;

public class Constants {

  public static final String PROJECT_BASE_PATH = "/projects";
  public static final String PROJECT_PATH_BY_ID = "/{identifier}";
  public static final String PROJECT_STAGES_ORDER = PROJECT_PATH_BY_ID + "/stage-orders";
  public static final String PROJECT_TAG_BY_NAME = "/{identifier}/tags/{tagName}";
  public static final String PROJECT_PATH_TO_DEPLOY = "/{identifier}/tags/{tagName}/run";
  public static final String PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH = "/{identifier}/tags/{tagName}/dependent-on";


  public static final String GITLAB_PROJECT_BASE_PATH = "/repositories";
  public static final String GITLAB_PROJECT_TAGS_BY_IDENTIFIER = "/{identifier}/tags";

  public static final String GITLAB_PROJECT_JOBS = PROJECT_BASE_PATH + PROJECT_PATH_BY_ID + "/jobs";
  public static final String GITLAB_PROJECT_JOB = GITLAB_PROJECT_JOBS + "/{jobId}";
  public static final String GITLAB_PROJECT_JOB_OPERATION =
      GITLAB_PROJECT_JOBS + "/{jobId}/{operation}";

  public static final String GITLAB_REPOSITORY_READ_FILE =
      PROJECT_BASE_PATH + PROJECT_PATH_BY_ID + "/repository/files/{filePath}";

}
