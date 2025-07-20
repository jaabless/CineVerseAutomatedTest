package com.cineverse.tests.media.contentDetails;


import com.cineverse.base.BaseTest;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Detail Operations")
public class GetMediaDetailTests extends BaseTest {

    @Test
    @Description("Test retrieving media details with valid mediaId")
    void testGetMediaDetailValidId() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("mediaId", DataValues.TWELVE)
                .when()
                .get(Endpoints.MEDIA_DETAILS)
                .then()
                .statusCode(StatusCodes.OK)
                .header("Content-Type", containsString("application/json"))
                .body("data.mediaId", equalTo(DataValues.TWELVE))
                .body("data.title", notNullValue())
                .body("data.synopsis", notNullValue())
                .body("data.releaseYear", notNullValue())
                .body("data.language", notNullValue())
                .body("data.averageRating", notNullValue())
                .body("data.genres", isA(List.class))
                .body("data.episodeList", isA(List.class))
                .body("data.castMembers", isA(List.class))
                .body("data.reviews", isA(List.class))
                .extract().response();

        // Verify response time
        assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < DataValues.TWO_THOUSAND, "Response time exceeded 2 seconds");
    }

    @Test
    @Description("Test retrieving media details with invalid mediaId")
    void testGetMediaDetailInvalidId() {
        given()
                .spec(requestSpec)
                .pathParam("mediaId", 999999)
                .when()
                .get("/api/v1/media/detail/{mediaId}")
                .then()
                .statusCode(404)
                .body("message", containsString("not found"));
    }

    @Test
    @Description("Test retrieving media details with negative mediaId")
    void testGetMediaDetailNegativeId() {
        given()
                .spec(requestSpec)
                .pathParam("mediaId", -1)
                .when()
                .get("/api/v1/media/detail/{mediaId}")
                .then()
                .statusCode(400)
                .body("message", containsString("invalid"));
    }

    @Test
    @Description("Test concurrent requests for media details")
    void testConcurrentGetDetailRequests() {
        // Implementation for concurrent testing using CompletableFuture or similar
        // This would test multiple simultaneous GET requests
    }
}