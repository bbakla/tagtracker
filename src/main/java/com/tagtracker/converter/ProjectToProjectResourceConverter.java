package com.tagtracker.converter;

import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.DependencyResource;
import com.tagtracker.model.resource.TagResource;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

//@Component
public class ProjectToProjectResourceConverter implements
    Converter<Project, ProjectResource> {

  private TagToTagResourceConverter tagConverter;

  public ProjectToProjectResourceConverter(
      TagToTagResourceConverter tagConverter) {
    this.tagConverter = tagConverter;
  }

  @Override
  public ProjectResource convert(Project source) {
    ProjectResource projectResource = new ProjectResource();

    projectResource.setProjectName(source.getProjectName());
    //applicationResource.setDeployedEnvironments(source.getDeployedEnvironments());
    projectResource.setEncodedPath(source.getEncodedPath());
    projectResource.setProjectId(source.getProjectId());
    projectResource.setCreatedDate(source.getCreatedDate());
    projectResource.setLastModifiedDate(source.getLastModifiedDate());
    projectResource.setCreatedBy(source.getCreatedBy());
    projectResource.setLastModifiedBy(source.getLastModifiedBy());

    if (source.getTags() != null) {

      Set<TagResource> tagResources = source.getTags()
          .stream()
          .map(sourceTag -> tagConverter.convert(sourceTag)).collect(
              Collectors.toSet());

      projectResource.setTags(tagResources);
    }

    return projectResource;
  }
}
