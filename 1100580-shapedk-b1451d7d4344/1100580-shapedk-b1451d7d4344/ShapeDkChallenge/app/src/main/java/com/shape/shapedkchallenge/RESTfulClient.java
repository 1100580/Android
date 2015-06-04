package com.shape.shapedkchallenge;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Pedro Bessa on 20/04/2015.
 */
public class RESTfulClient {

    private static ShapeAPI REST_CLIENT;
    private static String ROOT =
            "https://dl.dropboxusercontent.com/u/10746829/";

    static {
        setupRestClient();
    }

    private RESTfulClient() {}

    public static ShapeAPI get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(ShapeAPI.class);
    }
}
