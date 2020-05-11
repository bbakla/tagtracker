package com.tagtracker.converter;

import com.tagtracker.model.entity.Tag;
import com.tagtracker.model.entity.gitlab.GitlabTag;
import com.tagtracker.model.resource.TagResource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.core.convert.converter.Converter;

public class GitlabTagToTagConverter implements Converter<GitlabTag, Tag> {

  @Override
  public Tag convert(GitlabTag source) {
    Tag tag = new Tag();

    String description = source.getRelease() != null ? source.getRelease().getDescription() : null;

    tag.setTagName(source.getName());
    tag.setMessage(source.getMessage());
    tag.setReleaseMessage(description);

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      Date creationDate = sdf.parse(source.getCommitDate());
      tag.setCreatedDate(creationDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return tag;
  }
}
