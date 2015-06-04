package com.shape.shapedkchallenge;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ContactViewHolder> {

    private List<News> newsList;
    private Context c;

    public NewsAdapter(List<News> newsList, Context c) {
        this.newsList = newsList;
        this.c = c;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        News ni = newsList.get(i);

        contactViewHolder.vNewsTitle.setText(ni.getTitle());
        contactViewHolder.vNewsBody.setText(ni.getBody());

        if (ni.getState().equalsIgnoreCase("unread")){
            contactViewHolder.vNewsTitle.setTypeface(null,Typeface.BOLD); //only text style(only bold)
        }
        if (ni.getImage()!= null){
            contactViewHolder.vImageView.setImageDrawable(ni.getImage());
        }else{
            contactViewHolder.vImageView.setImageDrawable(c.getResources().getDrawable(R.drawable.ic_drawer));
        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vNewsTitle;
        protected TextView vNewsBody;
        protected ImageView vImageView;

        public ContactViewHolder(View v) {
            super(v);
            vNewsTitle =  (TextView) v.findViewById(R.id.newsTitle);
            vNewsBody = (TextView)  v.findViewById(R.id.newsBody);
            vImageView = (ImageView) v.findViewById(R.id.imagePlacement);
        }
    }
}