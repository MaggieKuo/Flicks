package com.mag.flicks.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;
import com.mag.flicks.Adapters.MovieRecyclerAdapter;
import com.mag.flicks.R;
import com.mag.flicks.models.Movie;
import com.mag.flicks.models.Result;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieRecyclerAdapter.MovieOnclick {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Result result = null;

    @BindView(R.id.view_movies)
    RecyclerView viewMovies;
    private MovieRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMovieList();

        setRecyclerView();

    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        viewMovies.setHasFixedSize(true);
        viewMovies.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MovieRecyclerAdapter(this);
        viewMovies.setAdapter(adapter);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    private void getMovieList() {
        Log.d(TAG, "getMovieList");

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/now_playing")
                .addQueryParameter("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<Result>() {
                             },
                        new ParsedRequestListener<Result>() {

                            @Override
                            public void onResponse(Result response) {

                                if (result==null)
                                    result = new Result();
                                else
                                    result.getResults().clear();

                                adapter.setResult(response);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.e(TAG, anError.getErrorDetail());


                            }
                        });
    }

    @Override
    public void onMovieImageClick(Movie movie) {

    }
}
