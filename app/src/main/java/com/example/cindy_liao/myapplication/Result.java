package com.example.cindy_liao.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity implements resultAdapter.onClickHandler{
    TextView movieTextView;
    String[] movies={"http://google.com","http://baidu.com","http://apple.com","http://apple.com",
            "http://apple.com","http://baidu.com","http://baidu.com","http://baidu.com","http://baidu.com"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//        movieTextView=(TextView) findViewById(R.id.movie_id);
//        String[] movie_results = {"http://google.com"};
//        for(String m: movie_results){
//            movieTextView.append(m);
//        }

        RecyclerView mRecycleView =(RecyclerView) findViewById(R.id.rv_results);

        resultAdapter adapter =new resultAdapter(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecycleView.setAdapter(adapter);
        mRecycleView.setLayoutManager(layoutManager);

        adapter.setMovieArray(movies);
        //movieTextView=(TextView) findViewById(R.id.tv_movieItem);


    }

    @Override
    public void click(String s) {
        openWebPage(s);
    }

//    public void onClickTextView(View v){
//        String url = movieTextView.getText().toString();
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
