package com.tagtracker.converter;

import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabUser;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import com.tagtracker.model.entity.tracker.Project;
import com.tagtracker.model.entity.tracker.Tag;
import com.tagtracker.model.resource.ProjectResource;
import com.tagtracker.model.resource.TagResource;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.core.convert.converter.Converter;

//@Component
public class GitlabProjectToProjectConverter implements
    Converter<GitlabProject, Project> {

  private GitlabTagToTagConverter tagConverter;

  public GitlabProjectToProjectConverter(
      GitlabTagToTagConverter tagConverter) {
    this.tagConverter = tagConverter;
  }


  @Override
  public Project convert(GitlabProject source) {
    Project project = new Project();

    project.setProjectName(source.getName());
    project.setDescription(source.getDescription() == null ? "" : source.getDescription());

    project.setEncodedPath(source.getPathWithNamespace());
    project.setRemoteProjectId(source.getId());
    //project.setCreatedDate(source.getCreatedAt());
    //project.setLastModifiedDate(source.getLastActivityAt());
    //project.setCreatedBy(source.);
    //project.setLastModifiedBy(source.getLastActivityAt());

    if (source.getTagList() != null) {

      Set<Tag> tags = source.getTagList()
          .stream()
          .map(gitlabTag -> {
            Tag tag = tagConverter.convert((GitlabTag) gitlabTag);
            tag.setProject(project);

            return tag;

          }).collect(
              Collectors.toSet());

      project.setTags(tags);
    }

    return project;
  }


}
