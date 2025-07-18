package com.cineverse.tests.performance;

import com.cineverse.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ThrottlingAndResponseTimeTest {
    @Test
    @DisplayName("Test response time under load")
    void testResponseTime() {
        IntStream.range(0, 20).parallel().forEach(i -> {
            given()
                    .when()
                    .get("/api/v1/movies")
                    .then()
                    .statusCode(200)
                    .time(lessThan(2000L));
        });
    }

}
