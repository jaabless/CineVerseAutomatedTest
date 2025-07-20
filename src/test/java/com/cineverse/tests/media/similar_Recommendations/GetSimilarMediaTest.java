package com.cineverse.tests.media.similar_Recommendations;


import com.cineverse.base.BaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("GET /api/v1/media/{contentId}/similar Tests")
public class GetSimilarMediaTest extends BaseTest {

    private final String ENDPOINT = "/api/v1/media/{contentId}/similar";

    @Test
    @DisplayName("Should return similar media for valid contentId")
    void getSimilarMedia_ValidId_Positive() {

        given()
                .accept(ContentType.JSON)
                .pathParam("contentId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.MEDIA_SIMILAR)
                .then()
                .statusCode(StatusCodes.OK )
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("data.size()", greaterThanOrEqualTo(DataValues.ZERO))
                .body("data[0].mediaId", notNullValue())
                .body("data[0].title", notNullValue())
                .body("data[0].mediaType", notNullValue())
                .body("data[0].genres", notNullValue())
                .body("data[0].thumbnailUrl", notNullValue());
    }

    @Test
    @DisplayName("Should return 404 for non-existing contentId")
    void getSimilarMedia_NonExistingId_Negative() {
        int nonExistingContentId = 999999;

        given()
                .accept(ContentType.JSON)
                .pathParam("contentId", nonExistingContentId)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(404)
                .body("success", equalTo(false))
                .body("error", not(empty()))
                .body("message", containsStringIgnoringCase("not found"));
    }

    @Test
    @DisplayName("Should return 400 for invalid contentId type")
    void getSimilarMedia_InvalidIdType_Negative() {
        given()
                .accept(ContentType.JSON)
                .pathParam("contentId", "invalid") // intentionally incorrect
                .when()
                .get("/api/v1/media/invalid/similar")
                .then()
                .statusCode(400); // or 422 depending on API behavior
    }

    @Test
    @DisplayName("Should return quickly under 2 seconds")
    void getSimilarMedia_Performance() {
        int validContentId = 1;

        given()
                .accept(ContentType.JSON)
                .pathParam("contentId", validContentId)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .time(lessThan(2000L));
    }
}
