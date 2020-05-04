package com.tagtracker.controller;

public class Constants {

  public static final String APPLICATION_BASE_PATH = "/applications";
  public static final String APPLICATION_PATH_BY_ID = "/{identifier}";
  public static final String APPLICATION_PATH_DELETE_TAG_BY_NAME = "/{identifier}/tags/{tagName}";
  public static final String APPLICATION_PATH_TO_DEPLOY = "/{identifier}/deploy/{environment}";
  public static final String APPLICATION_PATH_BY_ID_AND_DEPENDENCY_PATH = "/{identifier}/dependent_to/{dependentTo}";


  public static final String GITLAB_PROJECT_BASE_PATH = "/repositories";
  public static final String GITLAB_PROJECT_BY_IDENTIFIER = "/{identifier}";
  public static final String GITLAB_PROJECT_TAGS_BY_IDENTIFIER = "/{identifier}/tags";
  public static final String GITLAB_PROJECT_SPECIFIC_TAGS_BY_IDENTIFIER =
      GITLAB_PROJECT_TAGS_BY_IDENTIFIER + "/{tagName}";

}