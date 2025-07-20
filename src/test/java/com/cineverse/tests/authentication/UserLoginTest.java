package com.cineverse.tests.authentication;

import com.cineverse.base.BaseTest;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import com.cineverse.data.TestDataProvider;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("POST /api/v1/auth/login Tests")
public class UserLoginTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("com.cineverse.data.TestDataProvider#validLoginCredentials")
    @Story("LOGIN User")
    @DisplayName("Test user login with valid inputs")
    void test_validLoginCredentials(TestDataProvider.AuthLoginData authData, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(Endpoints.AUTH_LOGIN)
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body(DataValues.DATA_EMAIL, expectedStatusCode == StatusCodes.OK ? equalTo(authData.getEmail()) : anything())
                .body(DataValues.DATA_TOKEN, expectedStatusCode == StatusCodes.OK ? notNullValue() : anything())
                .body(DataValues.SUCCESS, expectedStatusCode == StatusCodes.OK ? equalTo(true) : anything())
                .header("Content-Type", containsString("application/json"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.TestDataProvider#invalidLoginCredentials")
    @Story("LOGIN User")
    @DisplayName("Test user login with invalid inputs")
    void test_invalidLoginCredentials(TestDataProvider.AuthLoginData authData, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(Endpoints.AUTH_LOGIN)
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body(DataValues.DATA, expectedStatusCode == StatusCodes.OK ? equalTo(nullValue()) : anything())
                .body(DataValues.SUCCESS, expectedStatusCode == StatusCodes.OK ? equalTo(false) : anything())
                .body(DataValues.MESSAGE, containsStringIgnoringCase("invalid"))
                .header("Content-Type", containsString("application/json"));
    }

}
