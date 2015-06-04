package com.shape.shapedkchallenge;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NewsFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        NewsFragment f = new NewsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_news, null);

        RecyclerView recList = (RecyclerView) root.findViewById(R.id.card_list);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        List<News> n = getArguments().getParcelableArrayList("news");

        RecyclerView.Adapter adapter = new NewsAdapter(n, getActivity().getBaseContext());
        recList.setAdapter(adapter);

        return root;
    }

}