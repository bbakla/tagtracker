package com.tagtracker.repository;

import com.tagtracker.model.entity.Tag;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

  Tag findTagByTagNameAndProjectProjectName(String tagName, String projectName);

  Tag findTagByTagNameAndProject_RemoteProjectId(String tagName, String projectId);


  @Transactional
  @Modifying
  @Query("delete from Tag t where t.tagName=:tagName")
  void deleteByTagName(@Param("tagName") String tagName);

  @Transactional
  @Modifying
  @Query("delete from Tag t where t.tagName=:tagName and t.project.remoteProjectId=:remoteProjectId")
  void deleteByTagNameAndProject_RemoteProjectId(String tagName, String remoteProjectId);

}
