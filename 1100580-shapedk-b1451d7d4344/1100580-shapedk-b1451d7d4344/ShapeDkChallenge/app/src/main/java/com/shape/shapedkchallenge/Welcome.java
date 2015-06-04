package com.shape.shapedkchallenge;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Welcome extends Fragment {

    String text = "";
    public static Fragment newInstance(Context context) {
        Welcome f = new Welcome();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_welcome, null);

        TextView username = (TextView) root.findViewById(R.id.user_name);

        if (getArguments()!= null){
            username.setText("Welcome! " + getArguments().getString("loggedUser"));
        }

        return root;
    }

}