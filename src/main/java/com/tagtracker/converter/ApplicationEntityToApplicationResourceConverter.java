package com.tagtracker.converter;

import com.tagtracker.model.entity.Application;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.model.resource.DependencyResource;
import com.tagtracker.model.resource.TagResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Component
public class ApplicationEntityToApplicationResourceConverter implements
    Converter<Application, ApplicationResource> {

  @Override
  public ApplicationResource convert(Application source) {
    ApplicationResource applicationResource = new ApplicationResource();

    applicationResource.setApplicationName(source.getApplicationName());
    applicationResource.setDeployedEnvironments(source.getDeployedEnvironments());
    applicationResource.setEncodedPath(source.getEncodedPath());
    applicationResource.setProjectId(source.getProjectId());
    applicationResource.setCreatedDate(source.getCreatedDate());
    applicationResource.setLastModifiedDate(source.getLastModifiedDate());
    applicationResource.setCreatedBy(source.getCreatedBy());
    applicationResource.setLastModifiedBy(source.getLastModifiedBy());

    TagResource tagResource = new TagResource(source.getTag().getTagName(),
        source.getTag().getMessage());
    applicationResource.setTag(tagResource);

    source.getDependentTo().stream().forEach(application -> {
      applicationResource.addDependency(convertDependency(application));
    });

    source.getDependentOnMe().stream().forEach(application -> {
      applicationResource.addDependentOnMe(convertDependency(application));
    });

    return applicationResource;
  }

  private DependencyResource convertDependency(Application application) {
    TagResource tagResource = new TagResource(application.getTag().getTagName(),
        application.getTag().getMessage());

    return new DependencyResource(application.getProjectId(), application.getApplicationName(),
        application.getEncodedPath(), tagResource);
  }
}
