package com.shape.shapedkchallenge;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddFragment extends Fragment {

    private Button button;
    private EditText t, b;
    List<News> n;

    public static Fragment newInstance(Context context) {
        AddFragment f = new AddFragment();
        return f;
    }


    private View.OnClickListener submit = new View.OnClickListener() {
        public void onClick(View v) {
            n.add(new News(t.getText().toString(), b.getText().toString(), ""));

            ((MainActivity)getActivity()).showNews();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_add, null);

        t = (EditText) root.findViewById(R.id.editText);
        b = (EditText) root.findViewById(R.id.editText2);

        button = (Button) root.findViewById(R.id.buttonSubmitNews);

        n = getArguments().getParcelableArrayList("news");

        button.setOnClickListener(submit);

        return root;
    }

}