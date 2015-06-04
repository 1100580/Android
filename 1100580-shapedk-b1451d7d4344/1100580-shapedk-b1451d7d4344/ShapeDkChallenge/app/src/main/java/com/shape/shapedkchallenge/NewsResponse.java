package com.shape.shapedkchallenge;

import java.util.List;

/**
 * Created by Pedro Bessa on 20/04/2015.
 */
public class NewsResponse {

    private List<News> data;

    public NewsResponse() {
    }

    public NewsResponse(List<News> data) {
        this.setData(data);
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

}

