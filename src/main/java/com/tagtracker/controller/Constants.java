package com.tagtracker.controller;

public class Constants {

  public static final String PROJECT_BASE_PATH = "/projects";
  public static final String PROJECT_PATH_BY_ID = "/{identifier}";
  public static final String PROJECT_PATH_DELETE_TAG_BY_NAME = "/{identifier}/tags/{tagName}";
  public static final String PROJECT_PATH_TO_DEPLOY = "/{identifier}/tags/{tagName}/deploy/{environment}";
  public static final String PROJECT_PATH_BY_ID_AND_DEPENDENCY_PATH = "/{identifier}/tags/{tagName}/dependent-to";
  public static final String PROJECT_PATH_BY_ID_AND_DEPENDENT_TO_ME_PATH = "/{identifier}/tags/{tagName}/dependent-to-me/{dependentToMe}";


  public static final String GITLAB_PROJECT_BASE_PATH = "/repositories";
  public static final String GITLAB_PROJECT_TAGS_BY_IDENTIFIER = "/{identifier}/tags";
  public static final String GITLAB_PROJECT_SPECIFIC_TAGS_BY_IDENTIFIER =
      GITLAB_PROJECT_TAGS_BY_IDENTIFIER + "/{tagName}";

}
