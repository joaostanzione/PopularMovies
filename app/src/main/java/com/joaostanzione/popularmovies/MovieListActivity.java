package com.joaostanzione.popularmovies;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joaostanzione.popularmovies.model.Movie;
import com.joaostanzione.popularmovies.model.MoviesResponse;
import com.joaostanzione.popularmovies.service.MovieService;
import com.joaostanzione.popularmovies.utils.PrefsUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private List<Movie> mMovies;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        assert mRecyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        //TODO: Ver quem vem antes...
        SharedPreferences sharedPref = PrefsUtils.getPrefs(this);
        String criteria = sharedPref.getString(PrefsUtils.KEY_CRITERIA, PrefsUtils.CRITERIA_DEFAULT_VALUE);

        setupRecyclerView();
        fetchMovies(criteria);

    }

    private void fetchMovies(String criteria) {
        MovieService.MovieAPI api = new MovieService().getAPI();
        Call<MoviesResponse> call = api.getPopularMovies(criteria);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                mMovies = moviesResponse.getResults();
                mMoviesRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                //TODO: Tratar falhas
            }
        });
    }

    private void setupRecyclerView() {
        int orientation = getResources().getConfiguration().orientation;

        if (mTwoPane) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(mMovies, mTwoPane, this);

        mRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: Ver quem vem antes...
        SharedPreferences sharedPref = PrefsUtils.getPrefs(this);
        String criteria = sharedPref.getString(PrefsUtils.KEY_CRITERIA, PrefsUtils.CRITERIA_DEFAULT_VALUE);
        if (criteria.equals(PrefsUtils.CRITERIA_VALUE_POPULAR)) {
            getMenuInflater().inflate(R.menu.top_rated, menu);
        } else {
            getMenuInflater().inflate(R.menu.popular, menu);
        }

        //invalidate options menu

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            changeSearchCriteria(PrefsUtils.CRITERIA_VALUE_POPULAR);
            return true;
        } else if (id == R.id.action_top_rated) {
            changeSearchCriteria(PrefsUtils.CRITERIA_VALUE_TOP_RATED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeSearchCriteria(String criteriaValue) {
        SharedPreferences.Editor editor = PrefsUtils.getEditor(this);
        editor.putString(PrefsUtils.KEY_CRITERIA, criteriaValue);
        editor.commit();
        recreate();
    }
}
