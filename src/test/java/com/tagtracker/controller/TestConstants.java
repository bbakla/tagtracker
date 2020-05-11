package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_BASE_PATH;

public class TestConstants {

  public static final String APPLICATION_PATH_BY_ID = PROJECT_BASE_PATH + "/%s/";
  public static final String APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE =
      PROJECT_BASE_PATH + "/%s/dependent_to/%s";

  public static final String APPLICATION_DEPLOY_PATH_TEMPLATE =
      PROJECT_BASE_PATH + "/%s/deploy/%s";

  public static final String APPLICATION_PATH_DELETE_TAG_BY_NAME = APPLICATION_PATH_BY_ID +
      "/tags/%s?deleteRemoteTag=%b";

  public static final String APPLICATION_CREATE_TAGS = APPLICATION_PATH_BY_ID + "/tags";

  public static final String GITLAB_REPO_GET_BY_ID = GITLAB_PROJECT_BASE_PATH + "/%s";
  public static final String GITLAB_REPO_GET_TAGS = GITLAB_REPO_GET_BY_ID + "/tags";
  public static final String GITLAB_REPO_GET_TAG_BY_ID = GITLAB_REPO_GET_TAGS + "/%s";
}
