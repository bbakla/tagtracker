package com.tagtracker.service.gitlab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.model.dto.JOB_OPERATION;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GitlabServiceTest {

  @Autowired
  private GitlabService gitlabService;

  @Test
  public void canDisplayTheProjects() throws Exception {
    GitlabProject project = gitlabService.getProjectFromGitlab("102943");

    assertNotNull(project);
    assertEquals("terraform", project.getName());
  }

  @Test
  public void canGetTagsOfAnProject() throws Exception {
    GitlabTag[] tags = gitlabService.getTagsOfARemoteRepository("102943");

    assertNotNull(tags);
    assertNotEquals(0, tags.length);
    assertTrue(Arrays.stream(tags).anyMatch(t -> t.getName().equals("v1.1.0")));
  }

  @Test
  public void canGetASpecificTagOfAnProject() throws Exception {
    GitlabTag tag = gitlabService.getTagOfARemoteRepository("102943", "v1.1.0");

    assertNotNull(tag);
    assertEquals("v1.1.0", tag.getName());
  }

  @Test
  public void canTagARepository() throws Exception {
    String tagName = "testTag";
    GitlabTag newTag = gitlabService
        .createTag("102943", new TagDto(tagName, "message for testTag", "* release note"));

    assertNotNull(newTag);
    assertEquals(tagName, newTag.getName());

    Thread.sleep(2000);

    gitlabService.deleteTag("102943", "testTag");
  }

  @Test
  public void canDeleteATagInARepository() throws Exception {
    GitlabTag newTag = gitlabService.createTag("102943",
        new TagDto("to_delete", "message for testTag", "* release note before deletion"));
    assertNotNull(newTag);

    Thread.sleep(2000);

    gitlabService.deleteTag("102943", newTag.getName());
  }

  @Test
  public void canGetJobsOfAProject() throws Exception {
    GitlabJob[] jobs = gitlabService.getProjectJobs("135330");

    assertTrue(jobs.length > 0);
    assertTrue(Arrays.stream(jobs).anyMatch(j -> j.getStage().equals("test")));
    var jobsOfTag = Arrays.stream(jobs).filter(j -> j.getStage().equals("build") && j.getTag())
        .collect(Collectors.toList());
    assertEquals(2, jobsOfTag.size());
  }

  @Test
  public void canPlayAJob() throws Exception {
    GitlabJob job = gitlabService.playAJob("135330", "30193005 ", JOB_OPERATION.play);

    assertEquals("build", job.getStage());
    assertNotNull(job);

  }
}
