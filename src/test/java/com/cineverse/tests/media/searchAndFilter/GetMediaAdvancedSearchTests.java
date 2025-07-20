package com.cineverse.tests.media.searchAndFilter;

import com.cineverse.base.BaseTest;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Advanced Search Operations")
public class GetMediaAdvancedSearchTests extends BaseTest {

    @Test
    @Story("GET Search Media")
    @DisplayName("Test advanced search with default parameters")
    void testGetAdvancedSearchWithNoParam() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(Endpoints.ADVANCED_SEARCH)
                .then()
                .statusCode(StatusCodes.OK)
                .header("Content-Type", containsString("application/json"))
                .body("data.pageable.pageNumber", equalTo(0))
                .body("data.pageable.pageSize", equalTo(10))
                .body("data.totalElements", greaterThanOrEqualTo(0))
                .extract().response();

        // Verify response time
        assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < 2000, "Response time exceeded 2 seconds");
    }

    @Test
    @Story("GET Search Media")
    @DisplayName("Test advanced search with query and genre filter")
    void testAdvancedSearchWithQueryAndGenre() {
        given()
                .spec(requestSpec)
                .queryParam("query", "Movie")
                .queryParam("genres", "COMEDY")
                .when()
                .get("/api/v1/media/advanced-search")
                .then()
                .statusCode(200)
                .body("content.title", everyItem(containsStringIgnoringCase("Movie")))
                .body("content.genre", everyItem(equalTo("COMEDY"))); // Assuming 'genre' is a field
    }

    @Test
    @Story("GET Search Media")
    @DisplayName("Test advanced search with invalid rating")
    void testAdvancedSearchWithInvalidRating() {
        given()
                .spec(requestSpec)
                .queryParam("rating", -1.0)
                .when()
                .get("/api/v1/media/advanced-search")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @Story("GET Search Media")
    @DisplayName("Test advanced search with future release year")
    void testAdvancedSearchWithFutureReleaseYear() {
        given()
                .spec(requestSpec)
                .queryParam("releaseYear", 2023)
                .when()
                .get("/api/v1/media/advanced-search")
                .then()
                .statusCode(200)
                .body("content.releaseYear", everyItem(lessThanOrEqualTo(2023))); // Adjust based on API behavior
    }

}