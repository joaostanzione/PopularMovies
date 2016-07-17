package com.joaostanzione.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joaostanzione.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesRecyclerViewAdapter
            extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> mMovies;
    private final boolean mTwoPane;
    private final AppCompatActivity mActivity;

    public MoviesRecyclerViewAdapter(List<Movie> movies, boolean twoPane, AppCompatActivity activity) {
            mMovies = movies;
        this.mTwoPane = twoPane;
        this.mActivity = activity;
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mMovie = mMovies.get(position);
            //TODO: baseUrl .... http://image.tmdb.org/t/p/w185//yySmUG29VgDdCROb9eer9L2kkKX.jpg
            Picasso.with(mActivity).load("http://image.tmdb.org/t/p/w185/"+mMovies.get(position).getPosterPath()).into(holder.mImageView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(MovieDetailFragment.ARG_SELECTED_MOVIE, holder.mMovie);
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        mActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailFragment.ARG_SELECTED_MOVIE, holder.mMovie);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(mActivity, holder.mImageView, "movie_poster");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            context.startActivity(intent, options.toBundle());
                        } else {
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public Movie mMovie;
            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image_view_poster);
            }

        }
    }