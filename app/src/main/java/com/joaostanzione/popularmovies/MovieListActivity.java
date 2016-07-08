package com.joaostanzione.popularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joaostanzione.popularmovies.model.Movie;
import com.joaostanzione.popularmovies.model.MoviesResponse;
import com.joaostanzione.popularmovies.service.MovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //TODO: Colocar no application?
        Fresco.initialize(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        fetchMovies((RecyclerView) recyclerView);

    }

    private void fetchMovies(final RecyclerView recyclerView) {
        MovieService.MovieAPI api = new MovieService().getAPI();
        Call<MoviesResponse> call = api.getPopularMovies();
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                mMovies = moviesResponse.getResults();
                setupRecyclerView(recyclerView);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new MoviesRecyclerViewAdapter(mMovies, mTwoPane, this));
    }
}
