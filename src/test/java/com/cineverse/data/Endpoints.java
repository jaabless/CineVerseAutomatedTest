package com.cineverse.data;

public class Endpoints {

    public static final String AUTH_LOGIN = "/api/v1/auth/login";
    public static final String AUTH_REGISTER = "/api/v1/auth/register";
    public static final String MOVIES_LIST = "/api/v1/movies";
    public static final String MEDIA_COMMENTS = "/api/v1/comments/media/{mediaId}";
    public static final String USER_RATING = "/api/v1/ratings/user/{userId}";
    public static final String MEDIA_TRAILER = "api/v1/media/{contentId}/trailer";
    public static final String USER_PROFILE = "/api/v1/users/profile";
    public static final String USER_UPDATE_PROFILE = "/api/v1/users/update-profile";
    public static final String MEDIA_LISTINGS = "/api/v1/media/listings";
    public static final String MEDIA_DETAILS = "/api/v1/media/detail/{mediaId}";
    public static final String MEDIA_SEARCH = "/api/v1/media/search";
    public static final String ADVANCED_SEARCH = "/api/v1/media/advanced-search";
    public static final String MEDIA_SIMILAR = "/api/v1/media/{contentId}/similar";
    public static final String MEDIA_TRENDING_NOW = "/api/v1/media/trending-now";
    public static final String MEDIA_TRAILER_INFO = "/api/v1/media/{contentId}/trailer/info";
    public static final String USER_RECOMMENDATIONS = "api/v1/media/{userId}/recommendations";



}
