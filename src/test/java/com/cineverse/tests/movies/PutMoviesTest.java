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
//public class PutMoviesTest extends BaseTest {
//    @Test
//    void updateMovie_Positive() {
//        Movie m = new Movie("Updated", "Drama", 2025);
//        given().contentType(JSON).body(m)
//                .when().put("/api/v1/movies/{id}", existingId)
//                .then().statusCode(200)
//                .body("title", equalTo("Updated"))
//                .body(matchesJsonSchemaInClasspath("schemas/movie-schema.json"));
//    }
//
//    @Test
//    void updateMovie_NotFound() {
//        given().contentType(JSON).body(new Movie("X", "Y", 2000))
//                .when().put("/api/v1/movies/{id}", "nonexist")
//                .then().statusCode(404);
//    }
//}
//
