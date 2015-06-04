package com.shape.shapedkchallenge;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro Bessa on 20/04/2015.
 */
public class Data implements Parcelable {
    private int _id;
    private String token;
    private String email;
    private String name;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String picture;

    public Data(int _id, String token, String email, String name, String first_name, String middle_name, String last_name, String picture) {
        this.set_id(_id);
        this.setToken(token);
        this.setEmail(email);
        this.setName(name);
        this.setFirst_name(first_name);
        this.setMiddle_name(middle_name);
        this.setLast_name(last_name);
        this.setPicture(picture);
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    protected Data(Parcel in) {
        _id = in.readInt();
        token = in.readString();
        email = in.readString();
        name = in.readString();
        first_name = in.readString();
        middle_name = in.readString();
        last_name = in.readString();
        picture = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(token);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(first_name);
        dest.writeString(middle_name);
        dest.writeString(last_name);
        dest.writeString(picture);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}