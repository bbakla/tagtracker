package com.tagtracker.converter;

import com.tagtracker.model.entity.Project;
import com.tagtracker.model.resource.ApplicationResource;
import com.tagtracker.model.resource.DependencyResource;
import org.springframework.core.convert.converter.Converter;

//@Component
public class ApplicationEntityToApplicationResourceConverter implements
    Converter<Project, ApplicationResource> {

  @Override
  public ApplicationResource convert(Project source) {
    ApplicationResource applicationResource = new ApplicationResource();

    applicationResource.setApplicationName(source.getProjectName());
    //applicationResource.setDeployedEnvironments(source.getDeployedEnvironments());
    applicationResource.setEncodedPath(source.getEncodedPath());
    applicationResource.setProjectId(source.getProjectId());
    applicationResource.setCreatedDate(source.getCreatedDate());
    applicationResource.setLastModifiedDate(source.getLastModifiedDate());
    applicationResource.setCreatedBy(source.getCreatedBy());
    applicationResource.setLastModifiedBy(source.getLastModifiedBy());

    /*if(source.getTags() != null) {
      TagResource tagResource = new TagResource(source.getTags().getTagName(),
          source.getTags().getMessage());
      applicationResource.setTag(tagResource);
    }*/


    /*source.getDependentTo().stream().forEach(application -> {
      applicationResource.addDependency(convertDependency(application));
    });

    source.getDependentOnMe().stream().forEach(application -> {
      applicationResource.addDependentOnMe(convertDependency(application));
    });
*/
    return applicationResource;
  }

  private DependencyResource convertDependency(Project project) {
  /*  TagResource tagResource = new TagResource(application.getTags().getTagName(),
        application.getTags().getMessage());
*/
  /*  return new DependencyResource(application.getProjectId(), application.getApplicationName(),
        application.getEncodedPath(), tagResource);*/

    return null;
  }
}
