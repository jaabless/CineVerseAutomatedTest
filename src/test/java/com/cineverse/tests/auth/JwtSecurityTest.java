package com.cineverse.tests.auth;
import com.cineverse.base.BaseTest;
import com.cineverse.utils.AuthUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class JwtSecurityTest {

    @Test
    @DisplayName("Should reject unauthorized access")
    void testUnauthorizedAccess() {
        given()
                .when()
                .get("/api/v1/users")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Should allow authorized access with JWT")
    void testAuthorizedAccess() {
        String token = AuthUtils.getJWTToken("admin", "admin123");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/users")
                .then()
                .statusCode(200);
    }

}
