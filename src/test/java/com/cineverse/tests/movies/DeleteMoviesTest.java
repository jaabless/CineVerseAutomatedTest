//package com.cineverse.tests.movies;
//
//import com.cineverse.base.BaseTest;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.http.ContentType.JSON;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//import static org.hamcrest.Matchers.*;
//
//public class DeleteMoviesTest extends BaseTest {
//    @Test
//    void deleteMovie_Positive() {
//        given().when().delete("/api/v1/movies/{id}", existingId)
//                .then().statusCode(204);
//    }
//
//    @Test
//    void deleteMovie_NotFound() {
//        given().when().delete("/api/v1/movies/{id}", "nope")
//                .then().statusCode(404);
//    }
//}
//
