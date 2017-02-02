package com.ex.popularmovies.common;

import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    //To get a specific movie basaed on its tmdb ID.
    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

    //To get the most popular or the top rated movies.
    @GET("/3/movie/{sort_by}")
    Call<Movies> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

}