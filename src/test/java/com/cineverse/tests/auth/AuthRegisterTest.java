package com.cineverse.tests.auth;

import com.cineverse.base.BaseTest;
import com.cineverse.data.TestDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("POST /api/v1/auth/register Tests")
public class AuthRegisterTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("com.cineverse.data.AuthTestData#validRegisterUsers")
    @DisplayName("Should register a new user successfully")
    void registerUser_Positive(String firstName, String lastName, String email, String password, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body("""
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(firstName, lastName, email, password))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(anyOf(is(200), is(201)));
//                .body("message", containsStringIgnoringCase("success"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.TestDataProvider#validRegisterUserCredentials")
    @Story("CREATE User")
    @DisplayName("Test user registration with valid inputs")
    void test_validRegisteringCredentials(TestDataProvider.AuthRegisterData authData, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body("data.firstName", expectedStatusCode ==200 ? equalTo(authData.getFirstName()) : anything())
                .body("data.lastName", expectedStatusCode == 200 ? equalTo(authData.getLastName()) : anything())
                .body("data.email", expectedStatusCode == 200 ? equalTo(authData.getEmail()) : anything())
                .header("Content-Type", containsString("application/json"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.TestDataProvider#invalidRegisterUserCredentials")
    @Story("CREATE User")
    @DisplayName("Test user registration with invalid inputs")
    void test_invalidRegisteringCredentials(TestDataProvider.AuthRegisterData authData, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body("message", containsStringIgnoringCase("error"))
                .header("Content-Type", containsString("application/json"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.TestDataProvider#validRegisterUserCredentials")
    @Description("Test user registration with invalid inputs")
    @Story("CREATE User")
    @DisplayName("Test user registration with existing user credentials")
    void testRegisteringExistingUser(TestDataProvider.AuthRegisterData authData, int expectedStatusCode) {
        given()
                .spec(requestSpec)
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body("firstName", expectedStatusCode == 200 ? equalTo(authData.getFirstName()) : anything())
                .body("lastName", expectedStatusCode == 200 ? equalTo(authData.getLastName()) : anything())
                .body("email", expectedStatusCode == 200 ? equalTo(authData.getEmail()) : anything())
                .header("Content-Type", containsString("application/json"));
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.AuthTestData#validRegisterUsers")
    @DisplayName("Success message for valid registration")
    void testSuccessMessage(String firstName, String lastName, String email, String password) {
        given()
                .spec(requestSpec)
                .body("""
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(firstName, lastName, email, password))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("message", containsStringIgnoringCase("success"));
    }

    @Test
    @Description("Test rate limiting for registration")
    @Story("CREATE User")
    @DisplayName("Test rate limiting for registration")
    void testRateLimitingRegister() {
        for (int i = 1; i <= 3; i++) {
            given()
                    .spec(requestSpec)
                    .body(new TestDataProvider.AuthRegisterData("Test", "User", "test" + i + "@example.com", "Password123@"))
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/api/v1/auth/register")
                    .then()
                    .statusCode(anyOf(equalTo(200)));
        }
    }


    @Test
    void registerUser_WithoutBody() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }

    @ParameterizedTest
    @MethodSource("com.cineverse.data.AuthTestData#invalidRegisterUsers")
    @DisplayName("Should fail with invalid user data")
    void registerUser_Negative(String firstName, String lastName, String email, String password) {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(firstName, lastName, email, password))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }
}
