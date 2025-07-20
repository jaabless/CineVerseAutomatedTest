package com.cineverse.tests.media.contentDetails;

import com.cineverse.base.AuthBaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetMediaTrailerTest extends AuthBaseTest {

    @Test
    @Story("GET Media Trailer")
    @DisplayName("Test retrieving media trailer by content ID")
    void test_GetMediaTrailer() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("contentId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.MEDIA_TRAILER)
                .then()
                .statusCode(StatusCodes.OK);
//                .body("success", equalTo(true));
    }
}

