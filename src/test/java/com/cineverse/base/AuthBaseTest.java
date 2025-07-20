package com.cineverse.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class AuthBaseTest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    protected static String token;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://cineverse-service-alb-staging-276074081.eu-west-1.elb.amazonaws.com";

        // Set up request logging and filters
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        if (token == null) {
            Response response = given()
                    .spec(requestSpec)
                    .body("""
                        {
                          "email": "newuseremail@gmail.com",
                          "password": "Password123@"
                        }
                    """)
                    .when()
                    .post("/api/v1/auth/login");

            // Log entire response for debugging
            response.then().log().body();

            // Ensure login success before extracting token
            if (response.getStatusCode() != 200) {
                throw new RuntimeException("❌ Login failed: " + response.getBody().asString());
            }

            // Extract token correctly
            token = response.then()
                    .extract()
                    .path("data.token");

//            System.out.println("🔑 JWT Token: " + token);
        }
    }

    protected String getAuthHeader() {
        return "Bearer " + token;
    }
}
