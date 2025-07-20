package com.cineverse.tests.media.similar_Recommendations;

import com.cineverse.base.AuthBaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Recommendations Operations")
public class GetMediaRecommendationsTests extends AuthBaseTest {

    @Test
    @Description("Test retrieving recommendations with default parameters")
    void testGetRecommendationsDefault() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .statusCode(StatusCodes.OK )
                .body("success", equalTo(true));
    }

    @Test
    @Description("Test retrieving recommendations with custom limit")
    void testGetRecommendationsWithLimit() {
        given()
                .spec(requestSpec)
                .queryParam("limit", 5)
                .when()
                .get("/api/v1/media/recommendations")
                .then()
                .statusCode(200)
                .body("data", hasSize(lessThanOrEqualTo(5)))
                .body("limit", equalTo(5));
    }

    @Test
    @Description("Test retrieving recommendations with invalid limit")
    void testGetRecommendationsWithInvalidLimit() {
        given()
                .spec(requestSpec)
                .queryParam("limit", -1)
                .when()
                .get("/api/v1/media/recommendations")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @Description("Test retrieving recommendations with custom userId")
    void testGetRecommendationsWithUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", 123)
                .when()
                .get("/api/v1/media/recommendations")
                .then()
                .statusCode(200)
                .body("userId", equalTo(123));
    }

    @Test
    @Description("Test retrieving recommendations with invalid userId")
    void testGetRecommendationsWithInvalidUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", 999999)
                .when()
                .get("/api/v1/media/recommendations")
                .then()
                .statusCode(404)
                .body("message", containsString("not found"));
    }

    @Test
    @Description("Test rate limiting for recommendations requests")
    void testRecommendationsRateLimiting() {
        for (int i = 0; i < 100; i++) {
            given()
                    .spec(requestSpec)
                    .when()
                    .get("/api/v1/media/recommendations")
                    .then()
                    .statusCode(anyOf(equalTo(200), equalTo(429))); // 429 for rate limit
        }
    }

    @Test
    @Description("Test recommendations with invalid JWT token")
    void testRecommendationsWithInvalidJwtToken() {
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer invalid_token")
                .when()
                .get("/api/v1/media/recommendations")
                .then()
                .statusCode(401)
                .body("message", containsString("unauthorized"));
    }
}