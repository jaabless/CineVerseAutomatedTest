package com.cineverse.tests.media.similar_Recommendations;

import com.cineverse.base.AuthBaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Media API - Recommendations Operations")
public class GetMediaRecommendationsTests extends AuthBaseTest {

    @Test
    @Story("GET Recommendations")
    @DisplayName("Test retrieving recommendations with default parameters")
    void testGetRecommendationsDefault() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK )
                .body("success", equalTo(true));
    }

    @Test
    @Story("GET Recommendations")
    @DisplayName("Test retrieving recommendations with custom limit")
    void testGetRecommendationsWithLimit() {
        given()
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .queryParam("limit", 5)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK )
                .body("success", equalTo(true));
    }


    @Test
    @Story("GET Recommendations")
    @DisplayName("Test retrieving recommendations with custom userId")
    void testGetRecommendationsWithUserId() {
        given()
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .queryParam("userId", 123)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK )
                .body("userId", equalTo(123));
    }

    @Test
    @Story("GET Recommendations")
    @DisplayName("Test retrieving recommendations with invalid userId")
    void testGetRecommendationsWithInvalidUserId() {
        given()
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .queryParam("userId", 999999)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK )
                .body("message", containsString("not found"));
    }


    @Test
    @Story("GET Recommendations")
    @DisplayName("Test recommendations with invalid JWT token")
    void testRecommendationsWithInvalidJwtToken() {
        given()
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.USER_RECOMMENDATIONS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK )
                .body("message", containsString("unauthorized"));
    }
}