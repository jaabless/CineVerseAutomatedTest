package com.cineverse.tests.userEngagement;
import com.cineverse.base.AuthBaseTest;
import com.cineverse.data.CommentTestData;
import com.cineverse.data.DataParams;
import com.cineverse.data.Endpoints;
import com.cineverse.data.StatusCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class PostMediaCommentTest extends AuthBaseTest{

    @Test
    @Story("Valid Comment Submission")
    @Description("Submit a valid comment to a specific media ID")
    void testPostValidComment() {
        int mediaId = 10;
        int userId = 1;
        String comment = "Amazing episode!";

        String requestBody = CommentTestData.validCommentBody(userId, mediaId, comment);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("mediaId", 1)
                .body(requestBody)
                .when()
                .post(Endpoints.MEDIA_COMMENTS)
                .then()
                .log().body()
                .statusCode(StatusCodes.CREATED)
                .body("success", equalTo(true))
                .body("data.comment", equalTo(comment));
    }

    @Test
    @Description("Test retrieving recommendations with default parameters")
    void test_GetUserRating() {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", getAuthHeader())
                .pathParam("mediaId", DataParams.CONTENT_ID_ONE)
                .body(
                        """
            {
              "userId": 101,
              "mediaId": 9999,
              "comment": "I hope this works?"
            }
        """
                )
                .when()
                .post(Endpoints.MEDIA_COMMENTS)
                .then()
                .log().body()
                .statusCode(StatusCodes.OK)
                .body("success", equalTo(true));
    }

//    @Test
//    @Story("Invalid Comment - Missing Fields")
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Try submitting a comment with missing fields")
//    @Order(2)
//    void testPostCommentMissingFields() {
//        int mediaId = 10;
//
//        String requestBody = CommentTestData.missingFieldsBody();
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/" + mediaId)
//                .then()
//                .statusCode(400)
//                .body("success", equalTo(false))
//                .body("message", containsString("Missing"));
//    }
//
//    @Test
//    @Story("Invalid Comment - Invalid Media ID")
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Try submitting a comment to a non-existing mediaId")
//    @Order(3)
//    void testPostCommentInvalidMediaId() {
//        int invalidMediaId = 9999;
//        int userId = 101;
//        String comment = "Trying on invalid media";
//
//        String requestBody = CommentTestData.validCommentBody(userId, invalidMediaId, comment);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/" + invalidMediaId)
//                .then()
//                .statusCode(anyOf(is(400), is(404)))
//                .body("success", equalTo(false));
//    }
//
//    @Test
//    @Story("Invalid Comment - Empty Comment")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Submit an empty comment and expect validation failure")
//    @Order(4)
//    void testPostEmptyComment() {
//        int mediaId = 5;
//        int userId = 101;
//
//        String requestBody = CommentTestData.emptyCommentBody(userId, mediaId);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/" + mediaId)
//                .then()
//                .statusCode(400)
//                .body("success", equalTo(false))
//                .body("message", containsString("comment cannot be empty"));
//    }
}
