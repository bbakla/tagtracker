package com.tagtracker.repository;

import com.tagtracker.model.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  List<Tag> findAllByProject_ProjectId(String projectId);

  Tag findTagByTagNameAndProjectProjectName(String tagName, String projectName);

  Tag findTagByTagNameAndProjectProjectId(String tagName, String projectId);

  void deleteTagByTagNameAndProjectProjectId(String tagName, String projectId);

}
