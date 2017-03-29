package com.example.cindy_liao.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Result extends AppCompatActivity implements resultAdapter.onClickHandler{
    TextView linkTextView;

    JSONObject json = null;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView mRecycleView;
    private resultAdapter adapter;
    private Boolean isMore= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        /* get contents from main page*/
        Intent resultIntent=getIntent();
        if (resultIntent.hasExtra(Intent.EXTRA_TEXT)){
            String intentText = resultIntent.getStringExtra(Intent.EXTRA_TEXT);
            setMovieList(intentText);


            System.out.println(movies);

            /*set RecyclerView and RecyclerView Adapter*/
            mRecycleView =(RecyclerView) findViewById(R.id.rv_results);
            adapter =new resultAdapter(movies,this);

            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            mRecycleView.setAdapter(adapter);
            mRecycleView.setLayoutManager(layoutManager);

            scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    if (isMore){
                        String more = loadNextDataFromApi(page);
                        isMore = setMovieList(more);
                        adapter.notifyDataSetChanged();
                    }

                }
            };
            // Adds the scroll listener to RecyclerView
            mRecycleView.addOnScrollListener(scrollListener);


        }

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public String loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        MyClientTask myClientTask2 = new MyClientTask();

        String[] input2 =MainActivity.input;
        input2[0] = Integer.toString(1+offset*10);

        String more_results = null;
        try{
            more_results=myClientTask2.execute(input2).get();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return more_results;

    }


    // String -> JSON -> ArrayList (Movie)
    public Boolean setMovieList(String text){
        int total_number=0;
        int start_number=0;
        try {
            json = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            total_number = json.getInt("total_num");


            start_number = json.getInt("start_no");
            JSONArray m = json.getJSONArray("movies");
            for (int i = 0; i < m.length(); i++) {
                Movie movie = new Movie();
                JSONObject jsonObject = m.getJSONObject(i);
                movie.setTitle(jsonObject.getString("movie_title"));
                movie.setGernes(jsonObject.getString("genres"));
                movie.setYear(jsonObject.getString("title_year"));
                movie.setLink(jsonObject.getString("movie_imdb_link"));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (start_number+10<total_number)
            return true;
        else
            return false;
    }



    @Override
    public void click(String s) {
        openWebPage(s);
    }

//    public void onClickTextView(View v){
//        String url = linkTextView.getText().toString();
//        openWebPage(url);
//    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
