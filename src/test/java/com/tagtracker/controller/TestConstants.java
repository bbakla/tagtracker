package com.tagtracker.controller;

import static com.tagtracker.controller.Constants.PROJECT_BASE_PATH;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_BASE_PATH;

public class TestConstants {

  public static final String PROJECT_PATH_BY_ID_TO_BE_FORMATTED = PROJECT_BASE_PATH + "/%s/";
  public static final String PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH_TEMPLATE =
      PROJECT_PATH_BY_ID_TO_BE_FORMATTED + "/tags/%s/dependent-on";

  public static final String PROJECT_DEPLOY_PATH_TEMPLATE =
      PROJECT_PATH_BY_ID_TO_BE_FORMATTED + "tags/%s/deploy/%s";

  public static final String APPLICATION_PATH_DELETE_TAG_BY_NAME =
      PROJECT_PATH_BY_ID_TO_BE_FORMATTED
          +
          "/tags/%s?deleteRemoteTag=%b";

  public static final String APPLICATION_CREATE_TAGS = PROJECT_PATH_BY_ID_TO_BE_FORMATTED + "/tags";

  public static final String GITLAB_REPO_GET_BY_ID = GITLAB_PROJECT_BASE_PATH + "/%s";
  public static final String GITLAB_REPO_GET_TAGS = GITLAB_REPO_GET_BY_ID + "/tags";
  public static final String GITLAB_REPO_GET_TAG_BY_ID = GITLAB_REPO_GET_TAGS + "/%s";
}
