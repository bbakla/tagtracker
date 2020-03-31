package com.tagtracker.service.gitlab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.gitlab.Project;
import com.tagtracker.model.entity.gitlab.RemoteTag;
import java.util.Arrays;
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
    Project project = gitlabService.getProjectFromGitlab("102943");

    assertNotNull(project);
    assertEquals("terraform", project.getName());
  }

  @Test
  public void canGetTagsOfAnProject() throws Exception {
    RemoteTag[] tags = gitlabService.getTagsOfProject("102943");

    assertNotNull(tags);
    assertNotEquals(0, tags.length);
    assertTrue(Arrays.stream(tags).anyMatch(t -> t.getName().equals("v1.1.0")));
  }

  @Test
  public void canGetASpecificTagOfAnProject() throws Exception {
    RemoteTag tag = gitlabService.getTagOfARepo("102943", "v1.1.0");

    assertNotNull(tag);
    assertEquals("v1.1.0", tag.getName());
  }

  @Test
  public void canTagARepository() throws Exception {
    String tagName = "testTag";
    RemoteTag newTag = gitlabService
        .createTag("102943", new TagDto(tagName, "message for testTag", "* release note"));

    assertNotNull(newTag);
    assertEquals(tagName, newTag.getName());

    Thread.sleep(2000);

    gitlabService.deleteTag("102943", "testTag");
  }

  @Test
  public void canDeleteATagInARepository() throws Exception {
    RemoteTag newTag = gitlabService.createTag("102943",
        new TagDto("to_delete", "message for testTag", "* release note before deletion"));
    assertNotNull(newTag);

    Thread.sleep(2000);

    gitlabService.deleteTag("102943", newTag.getName());

   /* Tag tag = gitlabService.getTagOfARepo("102943", newTag.getName());

    assertNull(tag);
*/

//     assertThrows(
//         OperationNotSuccessfulException.class, () -> gitlabService.getTagOfARepo("102943", newTag.getName()));

  }
}
