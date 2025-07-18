//package com.cineverse.tests.movies;
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
//public class PostMoviesTest extends BaseTest {
//    @ParameterizedTest
//    @MethodSource("com.cineverse.data.DataProviderUtils#moviePayloads")
////    void postMovie_Positive(Movie movie) {
////        given().contentType(JSON).body(movie)
////                .when().post("/api/v1/movies")
////                .then().statusCode(201)
////                .body("title", equalTo(movie.getTitle()))
////                .body(matchesJsonSchemaInClasspath("schemas/movie-schema.json"));
////    }
//
//    @Test
//    void postMovie_Negative_MissingField() {
//        given().contentType(JSON).body(Map.of("genre","Comedy"))
//                .when().post("/api/v1/movies")
//                .then().statusCode(400);
//    }
//}
//
