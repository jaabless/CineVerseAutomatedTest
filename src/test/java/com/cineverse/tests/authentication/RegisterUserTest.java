package com.cineverse.tests.authentication;

import com.cineverse.base.BaseTest;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import com.cineverse.data.TestDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("POST /api/v1/auth/register Tests")
public class RegisterUserTest extends BaseTest {


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
                .post(Endpoints.AUTH_REGISTER)
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body(DataValues.DATA_FIRSTNAME, expectedStatusCode ==StatusCodes.OK ? equalTo(authData.getFirstName()) : anything())
                .body(DataValues.DATA_LASTNAME, expectedStatusCode == StatusCodes.OK ? equalTo(authData.getLastName()) : anything())
                .body(DataValues.DATA_EMAIL, expectedStatusCode == StatusCodes.OK ? equalTo(authData.getEmail()) : anything())
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
                .post(Endpoints.AUTH_REGISTER)
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body(DataValues.MESSAGE, containsStringIgnoringCase("error"))
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
                .post(Endpoints.AUTH_REGISTER)
                .then()
                .statusCode(expectedStatusCode)
                .contentType(ContentType.JSON)
                .body(DataValues.DATA_FIRSTNAME, expectedStatusCode ==StatusCodes.OK ? equalTo(authData.getFirstName()) : anything())
                .body(DataValues.DATA_LASTNAME, expectedStatusCode == StatusCodes.OK ? equalTo(authData.getLastName()) : anything())
                .body(DataValues.DATA_EMAIL, expectedStatusCode == StatusCodes.OK ? equalTo(authData.getEmail()) : anything())
                .header("Content-Type", containsString("application/json"));
    }


}
