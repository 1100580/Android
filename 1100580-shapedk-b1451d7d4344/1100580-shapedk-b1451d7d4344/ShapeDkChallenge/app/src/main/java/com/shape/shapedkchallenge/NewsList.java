package com.shape.shapedkchallenge;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsList extends ArrayList<News> implements Parcelable {

    private static final long serialVersionUID = 663585476779879096L;

    public NewsList() {
    }

    @SuppressWarnings("unused")
    public NewsList(Parcel in) {
        this();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.clear();

        // First we have to read the list size
        int size = in.readInt();

        for (int i = 0; i < size; i++) {
            News r = new News(in.readString(), in.readString(), in.readString());
            this.add(r);
        }
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable.Creator<NewsList> CREATOR = new Parcelable.Creator<NewsList>() {
        public NewsList createFromParcel(Parcel in) {
            return new NewsList(in);
        }

        public NewsList[] newArray(int size) {
            return new NewsList[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();

        // We have to write the list size, we need him recreating the list
        dest.writeInt(size);

        for (int i = 0; i < size; i++) {
            News r = this.get(i);

            dest.writeString(r.getTitle());
            dest.writeString(r.getBody());
        }
    }
}