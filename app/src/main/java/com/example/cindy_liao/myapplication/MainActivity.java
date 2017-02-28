package com.example.cindy_liao.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et_movie_title, et_gernes, et_actor1, et_actor2, et_actor3, et_year, et_director, et_country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_movie_title=(EditText) findViewById(R.id.movie_title_id);
        et_gernes=(EditText) findViewById(R.id.genres_id);
        et_actor1=(EditText) findViewById(R.id.actor1_id);
        et_actor2=(EditText) findViewById(R.id.actor2_id);
        et_actor3=(EditText) findViewById(R.id.actor3_id);
        et_year=(EditText) findViewById(R.id.year_id);
        et_director=(EditText) findViewById(R.id.director_id);
        et_country=(EditText) findViewById(R.id.country_id);

    }
}
