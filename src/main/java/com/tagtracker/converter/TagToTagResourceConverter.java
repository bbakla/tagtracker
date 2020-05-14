package com.tagtracker.converter;

import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.resource.TagResource;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

public class TagToTagResourceConverter implements Converter<Tag, TagResource> {

  @Override
  public TagResource convert(Tag source) {
    var tagResource =
        new TagResource(source.getRemoteProjectId(), source.getTagName(), source.getMessage(),
            source.getReleaseMessage());

    tagResource.setDeployedEnvironments(source.getDeployedEnvironments());
    tagResource.setTagsDependentOn(convertDependencies(source.getDependentOn()));
    tagResource.setTagsDependentOnMe(convertDependencies(source.getDependentOnMe()));

    return tagResource;
  }

  private Set<TagResource> convertDependencies(Set<Tag> dependencies) {
    return dependencies.stream()
        .map(d -> new TagResource(d.getRemoteProjectId(), d.getTagName(), d.getMessage(),
            d.getReleaseMessage()))
        .collect(Collectors.toSet());
  }
}
