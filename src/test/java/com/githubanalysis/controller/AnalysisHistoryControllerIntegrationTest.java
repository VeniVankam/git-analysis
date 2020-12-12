package com.githubanalysis.controller;

import com.githubanalysis.GithubAnalysisApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(classes = {GithubAnalysisApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/create-test-data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-data.sql")
@ActiveProfiles("integration-test")
public class AnalysisHistoryControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String BASE_URI = "/github-analysis/owner/{owner}/repository/{repositoryName}/history";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testGetHistory() {
        final String owner = "test";
        final String repoName = "testRepo";
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
                .body("page", is(0))
                .body("pageSize", is(10))
                .body("totalPageCount", is(2))
                .body("totalEntityCount", is(11))
                .body("entities.size()", is(10))
                .body("entities[0].response.repositoryUrl", is("url7"));
        //@formatter:on
    }
}
