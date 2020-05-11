package com.tagtracker.repository;

import com.tagtracker.model.entity.Project;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  Project findProjectByProjectIdAndTagsTagName(String id, String tagName);

  Optional<Project> findProjectByProjectName(String projectName);

  // Application findApplicationById(String id);
  Optional<Project> findProjectByProjectId(String projectId);

  Optional<Project> findProjectByEncodedPath(String path);

  @Transactional
  void deleteByProjectId(String projectId);

  @Transactional
  void deleteByProjectIdAndTagsTagName(String id, String tagName);


}
