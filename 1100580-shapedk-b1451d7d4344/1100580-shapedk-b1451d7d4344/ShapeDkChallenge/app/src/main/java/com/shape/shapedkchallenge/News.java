package com.shape.shapedkchallenge;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro Bessa on 21/04/2015.
 */
public class News implements Parcelable {
    private int _id;
    private String state = "";
    private String created_at = "";
    private String picture = "";
    private String title = "";
    private String body = "";
    private Drawable image;

    private Data user;

    public News(){

    }

    public News(String t, String b, String i){
        this.setTitle(t);
        this.setBody(b);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Data getUser() {
        return user;
    }

    public void setUser(Data user) {
        this.user = user;
    }

    protected News(Parcel in) {
        _id = in.readInt();
        state = in.readString();
        created_at = in.readString();
        picture = in.readString();
        title = in.readString();
        body = in.readString();
        user = (Data) in.readValue(Data.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(state);
        dest.writeString(created_at);
        dest.writeString(picture);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeValue(user);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}