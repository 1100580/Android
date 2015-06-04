package com.shape.shapedkchallenge;

/**
 * Created by Pedro Bessa on 20/04/2015.
 */
public class LoginResponse {

    private Data data;


    public LoginResponse() {
    }

    public LoginResponse(Data data) {
        this.setData(data);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

