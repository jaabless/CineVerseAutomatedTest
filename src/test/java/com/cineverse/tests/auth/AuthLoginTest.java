package com.cineverse.tests.auth;

import com.cineverse.base.BaseTest;
import com.cineverse.data.TestDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("POST /api/v1/auth/login Tests")
public class AuthLoginTest extends BaseTest {

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
                .post("/api/v1/auth/login")
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body("data.email", expectedStatusCode == 200 ? equalTo(authData.getEmail()) : anything())
                .body("data.token", expectedStatusCode == 200 ? notNullValue() : anything())
                .body("success", expectedStatusCode == 200 ? equalTo(true) : anything())
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
                .post("/api/v1/auth/login")
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body("data", expectedStatusCode == 200 ? equalTo(nullValue()) : anything())
                .body("success", expectedStatusCode == 200 ? equalTo(false) : anything())
                .body("message", containsStringIgnoringCase("invalid"))
                .header("Content-Type", containsString("application/json"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.AuthTestData#validLoginUsers")
    @DisplayName("Should login with valid credentials")
    void loginUser_Positive(String email, String password) {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(email, password))
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.AuthTestData#invalidLoginUsers")
    @DisplayName("Should fail login with invalid credentials")
    void loginUser_Negative(String email, String password) {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(email, password))
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(401)
                .body("error", notNullValue());
    }
}
