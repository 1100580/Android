package com.shape.shapedkchallenge;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Pedro Bessa on 20/04/2015.
 */
public interface ShapeAPI {
    /*Asynch, cant use these unfortunately
    @GET("/login.json")
    void login(Callback<LoginResponse> callback);

    @GET("/news.json")
    void news(Callback<NewsResponse> callback);*/

    @GET("/login.json")
    void login(Callback<LoginResponse> callback);

    @GET("/news.json")
    NewsResponse news();
}
