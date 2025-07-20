package com.cineverse.tests.media.viewContentListings;




import com.cineverse.base.BaseTest;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Listing Operations")
public class GetMediaListingTests extends BaseTest {

    @Test
    @Description("Test retrieving media listings with default parameters")
    void testGetMediaListingsDefault() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(Endpoints.MEDIA_LISTINGS)
                .then()
                .statusCode(StatusCodes.OK)
                .header("Content-Type", containsString("application/json"))
                .body("data.totalElements", greaterThanOrEqualTo(DataValues.ZERO))
                .body("data.totalPages", greaterThanOrEqualTo(DataValues.ZERO))
                .extract().response();

        // Verify response time
        assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < DataValues.TWO_THOUSAND, "Response time exceeded 2 seconds");
    }

    @Test
    @Description("Test retrieving media listings with valid pagination")
    void testGetMediaListingsWithPagination() {
        given()
                .spec(requestSpec)
                .queryParam("pageSize", 10)
                .queryParam("pageNumber", 1)
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(200)
                .body("size", lessThanOrEqualTo(10))
                .body("page", equalTo(1))
                .body("content", hasSize(lessThanOrEqualTo(10)));
    }

    @Test
    @Description("Test retrieving media listings with invalid page number")
    void testGetMediaListingsWithInvalidPage() {
        given()
                .spec(requestSpec)
                .queryParam("pageNumber", -1)
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @DisplayName("Should fail with invalid sortBy value")
    void getListings_InvalidSortBy_Negative() {
        given()
                .queryParam("sortBy", "INVALID_SORT")
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }

    @Test
    @Description("Test sorting by title in ascending order")
    void testGetMediaListingsSortByTitleAsc() {
        given()
                .spec(requestSpec)
                .queryParam("sortBy", "SORT_BY_TITLE")
                .queryParam("sortDirection", "asc")
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(200)
                .body("content.title", everyItem(isA(String.class))); // Assuming 'title' is a field in content
    }

    @Test
    @Description("Test filtering by genre")
    void testGetMediaListingsWithGenreFilter() {
        given()
                .spec(requestSpec)
                .queryParam("genreFilter", "ACTION")
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(200)
                .body("content.genre", everyItem(equalTo("ACTION"))); // Assuming 'genre' is a field in content
    }

    @Test
    @DisplayName("Should fail with invalid genre")
    void getListings_InvalidGenre_Negative() {
        given()
                .queryParam("genreFilter", "UNKNOWN_GENRE")
                .when()
                .get("/api/v1/media/listings")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }

    @Test
    @Description("Test concurrent requests")
    void testConcurrentGetRequests() {
        // Implementation for concurrent testing using CompletableFuture or similar
        // This would test multiple simultaneous GET requests
    }
}