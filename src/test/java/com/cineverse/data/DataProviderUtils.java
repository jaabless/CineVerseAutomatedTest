//package com.cineverse.data;
//
//import io.restassured.http.ContentType;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.stream.Stream;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//public class DataProviderUtils {
//
//    public static Stream<Arguments> moviePayloads() {
//        return Stream.of(
//                Arguments.of(new Movie("Title A", "Action", 2023)),
//                Arguments.of(new Movie("Title B", "Comedy", 2022))
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("com.cineverse.data.DataProviderUtils#moviePayloads")
//    void postMovie_Positive(Movie movie) {
//        given()
//                .contentType(ContentType.JSON)
//                .body(movie)
//                .when()
//                .post("/api/v1/movies")
//                .then()
//                .statusCode(201)
//                .body("title", equalTo(movie.getTitle()));
//    }
//
//}
