package com.cineverse.tests.userEngagement;

import com.cineverse.base.AuthBaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserRating extends AuthBaseTest {

    @Test
    @Description("Test retrieving recommendations with default parameters")
    void test_GetUserRating() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("userId", DataParams.CONTENT_ID_ONE)
                .when()
                .get(Endpoints.USER_RATING)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK)
                .body("success", equalTo(true));
    }
}
