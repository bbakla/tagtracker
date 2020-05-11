package com.tagtracker.repository;

import com.tagtracker.TestSampleCreator;
import com.tagtracker.model.entity.Project;
import com.tagtracker.model.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TagRepositoryTests {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @AfterEach
  public void cleanUp() {
    tagRepository.deleteAll();
    projectRepository.deleteAll();
  }

  @Test
  public void canAddTagToAProject() throws Exception {
    Project project = TestSampleCreator.createAProjectWithNoDependencies(false);
    Project savedProject = projectRepository.save(project);

    Tag tag = TestSampleCreator.createTag("tagName", savedProject);
    Tag savedTag = tagRepository.save(tag);

    Project savedProjectWithTag = projectRepository
        .findProjectByProjectId(savedProject.getProjectId()).get();

    assertEquals(0, savedProject.getTags().size());
    assertEquals(1, savedProjectWithTag.getTags().size());
    assertEquals(tag.getId(), savedProjectWithTag.getTags().iterator().next().getId());
    assertEquals(tag.getTagName(), savedProjectWithTag.getTags().iterator().next().getTagName());
  }

}
