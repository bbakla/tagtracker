package com.tagtracker.config;

import com.tagtracker.converter.GibLabJobToJobConverter;
import com.tagtracker.converter.GibLabJobToJobResourceConverter;
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

    registry.addConverter(tagConverter);
    registry.addConverter(new ProjectToProjectResourceConverter(tagConverter));
    registry.addConverter(new GitlabTagToTagConverter());
    registry.addConverter(new GibLabJobToJobConverter());
    registry.addConverter(new GibLabJobToJobResourceConverter());
  }
}
