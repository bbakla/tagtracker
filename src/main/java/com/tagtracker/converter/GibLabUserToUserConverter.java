package com.tagtracker.converter;

import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabUser;
import com.tagtracker.model.entity.tracker.Job;
import com.tagtracker.model.entity.tracker.User;
import java.net.UnknownServiceException;
import org.springframework.core.convert.converter.Converter;

public class GibLabUserToUserConverter implements Converter<GitlabUser, User> {

  @Override
  public User convert(GitlabUser source) {
    User user = new User();
    user.setFullName(source.getName());
    user.setUserId(source.getId());
    user.setUserName(source.getUsername());

    return user;
  }
}
