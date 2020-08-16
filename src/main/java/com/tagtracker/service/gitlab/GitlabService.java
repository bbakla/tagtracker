package com.tagtracker.service.gitlab;

import static com.tagtracker.controller.Constants.GITLAB_PROJECT_JOB;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_JOBS;
import static com.tagtracker.controller.Constants.GITLAB_PROJECT_JOB_OPERATION;

import com.tagtracker.model.dto.JOB_OPERATION;
import com.tagtracker.model.dto.gitlab.TagDto;
import com.tagtracker.model.entity.gitlab.GitlabProject;
import com.tagtracker.model.entity.gitlab.pipelines.GitlabJob;
import com.tagtracker.model.entity.gitlab.tags.GitlabTag;

import com.tagtracker.service.ProjectNotFoundException;
import com.tagtracker.service.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

  //@Value("${PRIVATE_TOKEN}")
  @Autowired
  private String privateToken;

  public GitlabProject getProjectFromGitlab(String projectId) throws ProjectNotFoundException {

    try {
      WebClient.RequestBodySpec request =
          client.method(HttpMethod.GET).uri("/projects/{projectIdentifier}", projectId);
      WebClient.ResponseSpec responseSpec =
          request.accept(MediaType.APPLICATION_JSON).retrieve();

      return responseSpec.bodyToMono(GitlabProject.class).block();
    } catch (WebClientResponseException e) {
      throw new ProjectNotFoundException(e);
    }
  }

  public GitlabTag[] getTagsOfARemoteRepository(String projectId) {
    WebClient.RequestBodySpec request =
        (RequestBodySpec) client.get().uri("/projects/" + projectId + "/repository/tags");

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(GitlabTag[].class).block();
  }


  // should be refactored
  public GitlabTag getTagOfARemoteRepository(String projectId, String tagName)
      throws TagNotFoundException {
    WebClient.RequestBodySpec request =
        (RequestBodySpec)
            client.get().uri("/projects/" + projectId + "/repository/tags/" + tagName);

    try {
      Mono<GitlabTag> tagMono =
          request
              .accept(MediaType.APPLICATION_JSON)
              .retrieve()
              /*   .onStatus(
              HttpStatus::is4xxClientError,
              response ->
                  response
                      .bodyToMono(String.class)
                      .map(body -> new OperationNotSuccessfulException(body)))*/
              .bodyToMono(GitlabTag.class);

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

  public GitlabTag createTag(String projectId, TagDto tagDto) {
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
                            .queryParam("release_description", tagDto.getReleaseNotes())
                            .build(projectId));

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(GitlabTag.class).block();
  }

  public GitlabJob[] getProjectJobs(String projectId) {

    WebClient.RequestBodySpec request = (RequestBodySpec)
        client.get()
            .uri(uriBuilder -> uriBuilder.path(GITLAB_PROJECT_JOBS).build(projectId));

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(GitlabJob[].class).block();
  }

  public GitlabJob getProjectJob(String projectId, String jobId) {

    WebClient.RequestBodySpec request = (RequestBodySpec)
        client.get()
            .uri(uriBuilder -> uriBuilder.path(GITLAB_PROJECT_JOB).build(projectId, jobId));

    WebClient.ResponseSpec responseSpec = request.accept(MediaType.APPLICATION_JSON).retrieve();

    return responseSpec.bodyToMono(GitlabJob.class).block();
  }

  public GitlabJob playAJob(String projectId, String jobId, JOB_OPERATION operation) {
    WebClient.RequestBodySpec requestBodySpec = (RequestBodySpec)
        client.post()
            .uri(uriBuilder -> uriBuilder.path(GITLAB_PROJECT_JOB_OPERATION)
                .build(projectId, jobId, operation.name()));

    WebClient.ResponseSpec responseSpec = requestBodySpec.accept(MediaType.APPLICATION_JSON)
        .retrieve();

    return responseSpec.bodyToMono(GitlabJob.class).block();
  }

}
