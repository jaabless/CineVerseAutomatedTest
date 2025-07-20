package com.cineverse.tests.media.contentDetails;

import com.cineverse.base.BaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class GetTrailerDetailsTest extends BaseTest {

    @Test
    @DisplayName("Should return similar media for valid contentId")
    void getMediaTrailer_ValidId_Positive() {

        given()
                .accept(ContentType.JSON)
                .pathParam("contentId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.MEDIA_TRAILER_INFO)
                .then()
                .statusCode(StatusCodes.OK )
                .body("success", equalTo(true))
                .body("data", notNullValue())
                .body("data.url", notNullValue())
                .body("data.filename", notNullValue());
    }
}
