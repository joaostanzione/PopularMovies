package com.joaostanzione.popularmovies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joaostanzione.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetailFragment extends Fragment {

    public static final String ARG_SELECTED_MOVIE = "selected_movie";

    private Movie mSelectedMovie;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_SELECTED_MOVIE)) {
            mSelectedMovie = (Movie) getArguments().getSerializable(ARG_SELECTED_MOVIE);

            Activity activity = this.getActivity();
            mCollapsingToolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (mCollapsingToolbarLayout != null) {
                mCollapsingToolbarLayout.setTitle(mSelectedMovie.getTitle());
                setCollapsingToolbarImage(mSelectedMovie.getBackdropPath());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        if (mSelectedMovie != null) {
            //TODO data bind
            ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mSelectedMovie.getOverview());
            ((TextView) rootView.findViewById(R.id.movie_rating)).setText(String.valueOf(mSelectedMovie.getVoteAverage()));
            ((TextView) rootView.findViewById(R.id.release_date)).setText(mSelectedMovie.getReleaseDate());
            //TODO: baseUrl
            Uri uri = Uri.parse("http://image.tmdb.org/t/p/w185/"+mSelectedMovie.getPosterPath());
            ((SimpleDraweeView) rootView.findViewById(R.id.image_view_poster_detail)).setImageURI(uri);
        }

        return rootView;
    }

    private void setCollapsingToolbarImage(String url){
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500/"+url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable backDropDrawable = new BitmapDrawable(getContext().getResources(), bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mCollapsingToolbarLayout.setBackground(backDropDrawable);
                } else {
                    mCollapsingToolbarLayout.setBackgroundDrawable(backDropDrawable);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}