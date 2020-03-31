package com.tagtracker.repository;

import com.tagtracker.model.entity.Application;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  Application findApplicationByProjectIdAndTagTagName(String id, String tagName);

  // Application findApplicationById(String id);
  Optional<Application> findApplicationByProjectId(String projectId);

  Optional<Application> findApplicationByEncodedPath(String path);

  @Transactional
  void deleteByProjectId(String projectId);

  @Transactional
  void deleteByProjectIdAndTagTagName(String id, String tagName);
}
