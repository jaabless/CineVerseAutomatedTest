package com.cineverse.utils;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthUtils {
    public static String getJWTToken(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/api/v1/auth/login")
                .then()
                .extract()
                .path("token");
    }
}

