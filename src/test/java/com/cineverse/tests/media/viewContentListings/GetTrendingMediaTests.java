package com.cineverse.tests.media.viewContentListings;




import com.cineverse.base.BaseTest;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Trending Now Operations")
public class GetTrendingMediaTests extends BaseTest {

    @Test
    @Description("Test retrieving trending media with default parameters")
    void testGetTrendingNowDefault() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(Endpoints.MEDIA_TRENDING_NOW)
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("data", hasSize(greaterThanOrEqualTo(DataValues.ZERO)))
                .extract().response();

        // Verify response time
        assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < 2000, "Response time exceeded 2 seconds");
    }

    @Test
    @Description("Test retrieving trending media with custom limit")
    void testGetTrendingNowWithLimit() {
        given()
                .spec(requestSpec)
                .queryParam("limit", 5)
                .when()
                .get("/api/v1/media/trending-now")
                .then()
                .statusCode(200)
                .body("data", hasSize(lessThanOrEqualTo(5)))
                .body("limit", equalTo(5));
    }

    @Test
    @Description("Test retrieving trending media with invalid limit")
    void testGetTrendingNowWithInvalidLimit() {
        given()
                .spec(requestSpec)
                .queryParam("limit", -1)
                .when()
                .get("/api/v1/media/trending-now")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @Description("Test retrieving trending media with custom time range")
    void testGetTrendingNowWithTimeRange() {
        given()
                .spec(requestSpec)
                .queryParam("timeRange", "day")
                .when()
                .get("/api/v1/media/trending-now")
                .then()
                .statusCode(200)
                .body("timeRange", equalTo("day"));
    }

    @Test
    @Description("Test retrieving trending media with invalid time range")
    void testGetTrendingNowWithInvalidTimeRange() {
        given()
                .spec(requestSpec)
                .queryParam("timeRange", "invalid")
                .when()
                .get("/api/v1/media/trending-now")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @Description("Test rate limiting for trending now requests")
    void testTrendingNowRateLimiting() {
        for (int i = 0; i < 100; i++) {
            given()
                    .spec(requestSpec)
                    .when()
                    .get("/api/v1/media/trending-now")
                    .then()
                    .statusCode(anyOf(equalTo(200), equalTo(429))); // 429 for rate limit
        }
    }

    @Test
    @Description("Test trending now with invalid JWT token")
    void testTrendingNowWithInvalidJwtToken() {
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer invalid_token")
                .when()
                .get("/api/v1/media/trending-now")
                .then()
                .statusCode(401)
                .body("message", containsString("unauthorized"));
    }
}