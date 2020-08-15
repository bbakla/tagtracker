package com.tagtracker.repository;

import com.tagtracker.model.entity.tracker.Project;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  Project findProjectByRemoteProjectIdAndTagsTagName(String id, String tagName);

  Optional<Project> findProjectByProjectName(String projectName);

  // Application findApplicationById(String id);
  Optional<Project> findProjectByRemoteProjectId(String projectId);

  Optional<Project> findProjectByEncodedPath(String path);

  @Transactional
  void deleteByRemoteProjectId(String projectId);

  @Transactional
  void deleteByRemoteProjectIdAndTagsTagName(String id, String tagName);


}
