package com.tagtracker.config;

import com.tagtracker.converter.GibLabJobToJobConverter;
import com.tagtracker.converter.GibLabJobToJobResourceConverter;
import com.tagtracker.converter.GibLabUserToUserConverter;
import com.tagtracker.converter.GitlabProjectToProjectConverter;
import com.tagtracker.converter.GitlabTagToTagConverter;
import com.tagtracker.converter.ProjectToProjectResourceConverter;
import com.tagtracker.converter.TagToTagResourceConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    var tagConverter = new TagToTagResourceConverter();
    var gitlabTagToTagConverter = new GitlabTagToTagConverter();

    registry.addConverter(tagConverter);
    registry.addConverter(new ProjectToProjectResourceConverter(tagConverter));
    registry.addConverter(gitlabTagToTagConverter);
    registry.addConverter(new GibLabJobToJobConverter());
    registry.addConverter(new GibLabJobToJobResourceConverter());
    registry.addConverter(new GibLabUserToUserConverter());
    registry.addConverter(new GitlabProjectToProjectConverter(gitlabTagToTagConverter));
    registry.addConverter(new GibLabUserToUserConverter());
    registry.addConverter(new TagToTagResourceConverter());
  }
}
