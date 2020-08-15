package com.tagtracker.converter;

import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.resource.TagResource;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;

public class TagToTagResourceConverter implements Converter<Tag, TagResource> {

  @Override
  public TagResource convert(Tag source) {
    var tagResource =
        new TagResource(source.getRemoteProjectId(), source.getProjectName(), source.getTagName(),
            source.getMessage(),
            source.getReleaseMessage());

    //TODO: convert Job to JobResource
    /*tagResource.setDeployedEnvironments(source.getPipelines() == null ? new EnumMap<>(
        Stage.class) : source.getPipelines() );*/
    tagResource.setTagsDependentOn(convertDependencies(source.getDependentOn()));
    tagResource.setTagsDependentOnMe(convertDependencies(source.getRelatedTags()));

    return tagResource;
  }

  private Set<TagResource> convertDependencies(Set<Tag> dependencies) {
    return dependencies.stream()
        .map(d -> new TagResource(d.getRemoteProjectId(), d.getProjectName(), d.getTagName(),
            d.getMessage(),
            d.getReleaseMessage()))
        .collect(Collectors.toSet());
  }
}
