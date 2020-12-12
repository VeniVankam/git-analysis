package com.githubanalysis.controller;

import com.githubanalysis.GithubAnalysisApplication;
import com.githubanalysis.client.GithubApi;
import com.githubanalysis.domain.AnalysisHistory;
import com.githubanalysis.dtos.GithubRepoInfo;
import com.githubanalysis.dtos.ReadMeInfoDTO;
import com.githubanalysis.repository.AnalysisHistoryRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {GithubAnalysisApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/create-test-data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-data.sql")
@ActiveProfiles("integration-test")
public class GithubRepoAnalysisControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String BASE_URI = "/github-analysis/owner/{owner}/repository/{repositoryName}";

    @LocalServerPort
    private int port;

    @MockBean
    private GithubApi githubApi;

    @Autowired
    private AnalysisHistoryRepository analysisHistoryRepository;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testShouldReturnRepoAnalysis() {
        final String owner = "test";
        final String repoName = "testRepo";
        GithubRepoInfo repoInfo = new GithubRepoInfo("testRepo", "www.testRepo.com");
        when(githubApi.getRepoInfo(eq(owner), eq(repoName)))
                .thenReturn(repoInfo);

        ReadMeInfoDTO readMeInfoDTO = new ReadMeInfoDTO("www.readme.com", "content");
        when(githubApi.getReadMe(eq(owner), eq(repoName)))
                .thenReturn(readMeInfoDTO);

        List<Object> dummyPRs = Arrays.asList("PR1", "PR2");
        when(githubApi.getOpenPullRequests(eq(owner), eq(repoName)))
                .thenReturn(dummyPRs);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.set("Link", "<https://api.github.com/repositories?per_page=1&page=2>; rel=\"next\", <https://api.github.com/repositories?per_page=1&page=20461>; rel=\"last\"");
        ResponseEntity<List<Object>> dummyCommits = new ResponseEntity<>(Collections.singletonList("Commit"), header, HttpStatus.OK);
        when(githubApi.getCommits(eq(owner), eq(repoName), eq(1)))
                .thenReturn(dummyCommits);

        //@formatter:off
        given()
            .header("Authorization", "Bearer "+ getToken())
       .when()
            .pathParam("owner", owner)
            .pathParam("repositoryName", repoName)
            .get(BASE_URI)
            .prettyPeek()
       .then()
            .statusCode(HttpStatus.OK.value())
            .body("ownerName", is(owner))
            .body("repositoryName", is(repoInfo.getName()))
            .body("response.repositoryUrl", is(repoInfo.getUrl()))
            .body("response.openPullRequestCount", is(dummyPRs.size()))
            .body("response.commitsCount", is(20461))
            .body("response.readmeUrl", is(readMeInfoDTO.getUrl()))
            .body("response.readmeContent", is(readMeInfoDTO.getContent()))
            .body("searchedOn", notNullValue());
        //@formatter:on

        AnalysisHistory analysisHistory = analysisHistoryRepository.findFirstByLoginIdAndOwnerNameAndRepositoryNameOrderBySearchedOnDesc(TEST_LOGIN_ID, owner, repoName);
        assertEquals(owner, analysisHistory.getOwnerName());
        assertEquals(repoName, analysisHistory.getRepositoryName());
        assertEquals(TEST_LOGIN_ID, analysisHistory.getLoginId());
        assertNotNull(analysisHistory.getSearchedOn());
        assertNotNull(analysisHistory.getResponse());
        assertEquals(repoInfo.getUrl(), analysisHistory.getResponse().getRepositoryUrl());
        assertEquals(dummyPRs.size(), analysisHistory.getResponse().getOpenPullRequestCount());
        assertEquals(readMeInfoDTO.getUrl(), analysisHistory.getResponse().getReadmeUrl());
        assertEquals(readMeInfoDTO.getContent(), analysisHistory.getResponse().getReadmeContent());
    }
}
