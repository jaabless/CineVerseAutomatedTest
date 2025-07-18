package com.cineverse.tests.movies;

import com.cineverse.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("GET /api/v1/movies Tests")
public class GetMoviesTest extends BaseTest {

    @Test
    @DisplayName("Should return 200 and list of movies")
    void getAllMovies_Positive() {
        given()
                .when()
                .get("/api/v1/movies")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].title", notNullValue())
                .time(lessThan(2000L));
    }

    @Test
    @DisplayName("Should return 404 for invalid path")
    void getAllMovies_InvalidPath_Negative() {
        given()
                .when()
                .get("/api/v1/movies-invalid")
                .then()
                .statusCode(404);
    }

//    @Test
//    void getMovieById_Positive(@MethodSource("com.cineverse.data.DataProviderUtils#existingMovieIds") String id) {
//        given().when().get("/api/v1/movies/{id}", id)
//                .then().statusCode(200).body("id", equalTo(id));
//    }

    @Test
    void getMovieById_NotFound() {
        given().when().get("/api/v1/movies/{id}", "not_exist")
                .then().statusCode(404);
    }


}
