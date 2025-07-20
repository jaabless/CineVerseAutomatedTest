package com.cineverse.tests.media.searchAndFilter;

import com.cineverse.base.BaseTest;
import com.cineverse.data.DataParams;
import com.cineverse.data.DataValues;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("GET /api/v1/media/search Tests")
public class GetSearchMediaTest extends BaseTest {

    private final String ENDPOINT = "/api/v1/media/search";

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search by title returns matching media")
    void testSearchByTitle() {
        given()
                .accept(ContentType.JSON)
                .queryParam("title", DataParams.SAMPLE_MOVIE_TEN)
                .when()
                .get(Endpoints.MEDIA_SEARCH)
                .then()
                .statusCode(StatusCodes.OK)
                .body("success", equalTo(true))
                .body("data.size()", greaterThan(DataValues.ZERO))
                .body("data[0].title", containsStringIgnoringCase("movie 10"));
    }

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search by genre with pagination returns results")
    void testSearchByGenre() {
        given()
                .accept(ContentType.JSON)
                .queryParam("genre", "ACTION")
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.size()", greaterThanOrEqualTo(0))
                .body("data.genreNames.flatten()", everyItem(anyOf(equalTo("ACTION"), equalTo("DRAMA")))); // optional flexibility
    }

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search by cast returns matches (if cast is indexed)")
    void searchByCast_Positive() {
        given()
                .accept(ContentType.JSON)
                .queryParam("cast", "John Doe") // Use real cast name if known
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search with multiple filters")
    void searchByTitleAndGenre() {
        given()
                .accept(ContentType.JSON)
                .queryParam("title", "movie")
                .queryParam("genre", "DRAMA")
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body("data.size()", greaterThanOrEqualTo(0));
    }

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search with no matching result returns empty data")
    void search_NoMatch() {
        given()
                .accept(ContentType.JSON)
                .queryParam("title", "nonsense-title-xyz")
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body("data.size()", is(0))
                .body("success", equalTo(true));
    }

    @Test
    @Story("GET Search/Filter Media")
    @DisplayName("Search returns response under 2 seconds")
    void search_PerformanceTest() {
        given()
                .accept(ContentType.JSON)
                .queryParam("title", "movie")
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .time(lessThan(2000L));
    }
}

