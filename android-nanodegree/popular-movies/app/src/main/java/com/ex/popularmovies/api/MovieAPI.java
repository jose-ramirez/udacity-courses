package com.ex.popularmovies.api;

import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Movies;
import com.ex.popularmovies.models.Reviews;
import com.ex.popularmovies.models.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    /**
     * Gets the info associated with a given movie.
     *
     * @param apiKey The TMDB key needed to authorize the requests.
     * @param movieId The TMDB id of the movie.
     * */
    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") int movieId, @Query("api_key") String apiKey);

    /**
     * Gets a list of movies based on the search criteria: top rated or most popular.
     *
     * @param apiKey The TMDB key needed to authorize the requests.
     * @param sortBy It can be "popular" or "top_rated".
     * */
    @GET("/3/movie/{sort_by}")
    Call<Movies> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);


    /**
     * Gets the videos (i.e., trailers, etc) associated with a given movie.
     *
     * @param apiKey The TMDB key needed to authorize the requests.
     * @param movieId The TMDB id of the movie.
     * */
    @GET("/3/movie/{movieId}/videos")
    Call<Videos> getMovieVideos(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    /**
     * Gets the reviews associated with a given movie.
     *
     * @param apiKey The TMDB key needed to authorize the requests.
     * @param movieId The TMDB id of the movie.
     * */
    @GET("/3/movie/{movieId}/reviews")
    Call<Reviews> getMovieReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}