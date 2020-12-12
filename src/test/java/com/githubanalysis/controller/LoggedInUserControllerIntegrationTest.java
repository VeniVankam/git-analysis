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

@SpringBootTest(classes = {GithubAnalysisApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/create-test-data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-test-data.sql")
@ActiveProfiles("integration-test")
class LoggedInUserControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String USER_BASE_URL = "/user/me";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testShouldThrowUnAuthorized() {
        //@formatter:off
        given()
        .when()
            .get(USER_BASE_URL)
            .prettyPeek()
        .then()
             .statusCode(HttpStatus.UNAUTHORIZED.value());
        //@formatter:on
    }

    @Test
    void testUserMe() {
        //@formatter:off
        given()
            .header("Authorization", "Bearer "+ getToken())
        .when()
            .get(USER_BASE_URL)
            .prettyPeek()
        .then()
             .statusCode(HttpStatus.OK.value())
            .body("loginId", is("testIntegration"))
            .body("name", is("Integration Test"))
            .body("email", is("testIntegration@test.com"))
            .body("image", is("image"));
        //@formatter:on
    }

    @Test
    void testUserThrowUnAuthorizedForInvalidToken() {
        //@formatter:off
        given()
            .header("Authorization", "Bearer ")
        .when()
            .get(USER_BASE_URL)
            .prettyPeek()
        .then()
             .statusCode(HttpStatus.UNAUTHORIZED.value());
        //@formatter:on
    }
}
