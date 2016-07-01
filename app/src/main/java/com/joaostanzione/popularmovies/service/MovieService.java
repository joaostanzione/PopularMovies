package com.joaostanzione.popularmovies.service;

import com.joaostanzione.popularmovies.BuildConfig;
import com.joaostanzione.popularmovies.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MovieService {

     private static String baseUrl = "https://api.themoviedb.org/3/";
     private MovieAPI movieAPI;

     public MovieService() {
          this(baseUrl);
     }

     public MovieService(String baseUrl) {

          Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .build();

          movieAPI = retrofit.create(MovieAPI.class);
     }

     public MovieAPI getAPI() {
          return movieAPI;
     }

     public interface MovieAPI {
          @GET("movie/popular?api_key="+ BuildConfig.TMDB_API_KEY)
          Call<MoviesResponse> getPopularMovies();
     }
}