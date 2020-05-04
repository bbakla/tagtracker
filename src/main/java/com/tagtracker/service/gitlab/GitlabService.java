package com.tagtracker.service.gitlab;

import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.gitlab.Project;
import com.tagtracker.model.entity.gitlab.RemoteTag;

import com.tagtracker.service.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class GitlabService {

  @Autowired
  private WebClient client;

  @Value("${PRIVATE_TOKEN}")
  private String privateToken;

  public Project getProjectFromGitlab(String projectId) throws ProjectNotFoundException {

    try {
      WebClient.RequestBodySpec request =
          client.method(HttpMethod.GET).uri("/projects/{projectIdentifier}", projectId);
      WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

      return responseSpec.bodyToMono(Project.class).block();
    } catch (WebClientResponseException e) {
      throw new ProjectNotFoundException(e);
    }
  }

  public RemoteTag[] getTagsOfProject(String projectId) {
    WebClient.RequestBodySpec request =
        (RequestBodySpec) client.get().uri("/projects/" + projectId + "/repository/tags");

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(RemoteTag[].class).block();
  }

  // should be refactored
  public RemoteTag getTagOfARepo(String projectId, String tagName)
      throws TagNotFoundException {
    WebClient.RequestBodySpec request =
        (RequestBodySpec)
            client.get().uri("/projects/" + projectId + "/repository/tags/" + tagName);

    try {
      Mono<RemoteTag> tagMono =
          request
              .accept(MediaType.APPLICATION_JSON)
              .retrieve()
              /*   .onStatus(
              HttpStatus::is4xxClientError,
              response ->
                  response
                      .bodyToMono(String.class)
                      .map(body -> new OperationNotSuccessfulException(body)))*/
              .bodyToMono(RemoteTag.class);

      return tagMono.block();

    } catch (WebClientResponseException e) {
      throw new TagNotFoundException(e);
    }
  }

  public void deleteTag(String projectId, String tagName) {
    WebClient.RequestBodySpec request =
        (RequestBodySpec)
            client
                .delete()
                .uri(
                    uriBuilder ->
                        uriBuilder
                            .path("/projects/{projectId}/repository/tags/{tagName}")
                            .build(projectId, tagName));

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    ResponseSpec body =
        responseSpec.onStatus(
            status -> !HttpStatus.NO_CONTENT.equals(status),
            response ->
                (Mono<? extends Throwable>)
                    response
                        .toEntity(String.class)
                        .subscribe(entity -> System.out.println(entity)));

    String responseBody = body.bodyToMono(String.class).block();

    if (responseBody != null) {
      throw new IllegalArgumentException(responseBody);
    }
  }

  public RemoteTag createTag(String projectId, TagDto tagDto) {
    WebClient.RequestBodySpec request =
        (RequestBodySpec)
            client
                .post()
                .uri(
                    uriBuilder ->
                        uriBuilder
                            .path("/projects/{projectId}/repository/tags")
                            .queryParam("tag_name", tagDto.getTagName())
                            .queryParam("ref", "master")
                            .queryParam("message", tagDto.getMessage())
                            .queryParam("release_description", tagDto.getReleaseNote())
                            .build(projectId));

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(RemoteTag.class).block();
  }

  public Project getProject() {
    Project project =
        WebClient.builder()
            .build()
            .get()
            .uri("https://code.siemens.com/api/v4/projects/102943")
            .header("PRIVATE-TOKEN", privateToken)
            .retrieve()
            .bodyToMono(Project.class)
            .block();

    System.out.println("sdfsdfsdfsdf");
    System.out.println(project.getName());
    //    project.subscribe((p -> System.out.println(p.getName())));

    return null;
  }
}