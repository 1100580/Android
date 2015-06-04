package com.shape.shapedkchallenge;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends FragmentActivity {

    private List<News> newsList = new ArrayList<>();
    private Data user;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle ="Welcome";
    private TextView headertext;
    private ImageView iv;


    final String[] data ={"one","two"};
    final String[] fragments ={
            "com.shape.shapedkchallenge.Welcome",
            "com.shape.shapedkchallenge.NewsFragment"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(mTitle);

        GetNewsTask g = new GetNewsTask();
        g.execute();

        user = (Data) getIntent().getExtras().get("loggedUser");

        ArrayAdapter adapter = new ArrayAdapter(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data);

        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        final ListView navList = (ListView) findViewById(R.id.drawer);
        View header = (View)getLayoutInflater().inflate(R.layout.list_header, null);

        navList.addHeaderView(header);
        //headertext.setText(user.getFirst_name());
        navList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawer,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mTitle);
            }
        };

        drawer.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        navList.setAdapter(adapter);
        navList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
                drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
                    @Override
                    public void onDrawerClosed(View drawerView){
                        super.onDrawerClosed(drawerView);

                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        switch(pos){
                            case 1:
                                mTitle = "Welcome";
                                getActionBar().setTitle(mTitle);
                                Fragment welcome = Welcome.newInstance(MainActivity.this);

                                bundle.putString("loggedUser", user.getFirst_name() + " " + user.getMiddle_name() + " " + user.getLast_name());
                                welcome.setArguments(bundle);

                                tx.replace(R.id.main, welcome);
                                tx.commit();
                                break;
                            case 2:
                                mTitle = "News";
                                getActionBar().setTitle(mTitle);
                                Fragment f = NewsFragment.newInstance(MainActivity.this);
                                bundle.putParcelableArrayList("news", (ArrayList) newsList);
                                f.setArguments(bundle);

                                tx.replace(R.id.main, f);
                                tx.commit();
                                break;
                        }

                    }
                });
                drawer.closeDrawer(navList);
            }
        });

        //first fragment is welcome
        Fragment first = Welcome.newInstance(MainActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString("loggedUser", user.getFirst_name() + " " + user.getMiddle_name() + " " + user.getLast_name());
        first.setArguments(bundle);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,first);
        tx.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        if(item.getItemId() == R.id.action_example){
            Bundle bundle = new Bundle();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            getActionBar().setTitle(mTitle);
            Fragment f = AddFragment.newInstance(MainActivity.this);
            bundle.putParcelableArrayList("news", (ArrayList) newsList);
            f.setArguments(bundle);

            tx.replace(R.id.main, f);
            tx.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showNews(){
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        mTitle = "News";
        getActionBar().setTitle(mTitle);

        Fragment f = NewsFragment.newInstance(MainActivity.this);
        bundle.putParcelableArrayList("news", (ArrayList) newsList);
        f.setArguments(bundle);

        tx.replace(R.id.main, f);
        tx.commit();
    }

    public class GetNewsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            newsList = RESTfulClient.get().news().getData();

            new DownloadImage().execute(newsList);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {
        }
    }

    private class DownloadImage extends AsyncTask<List<News>, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(List<News>... ln) {
            Bitmap bitmap = null;
            InputStream input;
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 128;
            options.inJustDecodeBounds = true;

            for(int i = 0; i < (ln[0].size()); i++){
                String imageURL = ln[0].get(i).getPicture();
                try {
                    // Download Image from URL
                    input = new java.net.URL(imageURL).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);
                    ln[0].get(i).setImage(new BitmapDrawable(getResources(), bitmap));
                    System.out.println("Image is " + ln[0].get(i).getImage());
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            System.out.println(newsList.get(0).getImage());
        }
    }
}