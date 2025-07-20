package com.cineverse.data;


public class CommentTestData {

    public static String validCommentBody(int userId, int mediaId, String comment) {
        return """
            {
              "userId": %d,
              "mediaId": %d,
              "comment": "%s"
            }
        """.formatted(userId, mediaId, comment);
    }

    public static String missingFieldsBody() {
        return """
            {
              "userId": 101
            }
        """;
    }

    public static String emptyCommentBody(int userId, int mediaId) {
        return """
            {
              "userId": %d,
              "mediaId": %d,
              "comment": ""
            }
        """.formatted(userId, mediaId);
    }
}

