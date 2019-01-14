package com.ex.popularmovies.api;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.ex.popularmovies.BuildConfig;
import com.ex.popularmovies.R;
import com.ex.popularmovies.data.MovieContract;
import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Movies;
import com.ex.popularmovies.models.Reviews;
import com.ex.popularmovies.models.Videos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 29/01/17.
 */

public class MovieGetter extends Observable {

    private Gson gson;

    private Retrofit RFAdapter;

    private MovieAPI ma;

    private Context ctx;

    private String apiKey;

    private OkHttpClient httpConfig;

    private static final String LOG_TAG = MovieGetter.class.getSimpleName();

    public MovieGetter(Observer o){
        this.ctx = (Context) o;

        addObserver(o);

        this.apiKey = BuildConfig.TMDB_API_KEY;


        //Our JSON converter.
        this.gson = new GsonBuilder().create();

        //waits 5 seconds to connect or to get the data if it connects.
        this.httpConfig = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        //Our movie getter.
        this.RFAdapter = new Retrofit.Builder()
                .baseUrl(this.ctx.getString(R.string.tmdb_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(this.httpConfig)
                .build();

        this.ma = this.RFAdapter.create(MovieAPI.class);
    }

    /**
    * Gets a movie based on the ID passed to it, and notifies to anyone
    * listening about its results (whether it fetched a movie or not).
    * */
    public void getMovie(int id){
        Callback<Movie> movieCallback = new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setChanged();
                notifyObservers(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                setChanged();
                notifyObservers(t);
            }
        };

        ma.getMovie(id, this.apiKey).enqueue(movieCallback);
    }

    /**
    * Gets a movie list based on the sort criteria specified in the settings:
    * most popular movies first, or top rated movies first. It also notifies
    * to any class listening about the results of the query, i.e., the list
    * of movies, or an exception.
    * */
    public void getMovies(){

        String criteria =
        PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(
                this.ctx.getString(R.string.sort_by_key), this.ctx.getString(R.string.default_sort_criteria));

        Callback<Movies> movieCallback = new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                setChanged();
                notifyObservers(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                setChanged();
                notifyObservers(t);
            }
        };

        ma.getMovies(criteria, this.apiKey).enqueue(movieCallback);
    }

    /**
    * Gets a movie's video list based on the id of the movie we're searching
    * the videos for. It also notifies to any class listening about the results
    * of the query, i.e., the list of videos, or an exception.
    *
    * @param movieId The id of the movie the videos are being searched for.
    * */
    public void getMovieVideos(int movieId){

        Callback<Videos> movieCallback = new Callback<Videos>() {

            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                setChanged();
                notifyObservers(response.body());
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                setChanged();
                notifyObservers(t);
            }
        };

        ma.getMovieVideos(movieId, this.apiKey).enqueue(movieCallback);
    }


    /**
     * Gets a movie's reviews based on the id of the movie we're searching
     * the reviews for. It also notifies to any class listening about the results
     * of the query, i.e., the list of reviews, or an exception.
     *
     * @param movieId The id of the movie the reviews are being searched for.
     * */
    public void getMovieReviews(int movieId){

        Callback<Reviews> movieCallback = new Callback<Reviews>() {

            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                setChanged();
                notifyObservers(response.body());
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                setChanged();
                notifyObservers(t);
            }
        };

        ma.getMovieReviews(movieId, this.apiKey).enqueue(movieCallback);
    }

    public void getFavorites() {
        Uri favoritesUri = MovieContract.FavoritesEntry.CONTENT_URI;

        //For now, only the ids of the favorite movies are saved, so a
        // full db query is the same as choosing my favorite movies.
        final Cursor c = this.ctx.getContentResolver().query(favoritesUri, null, null, null, null);

        //Attempt at downloading all of my favorites's info, asynchronously.
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Movies favMovies = new Movies();
                List<Movie> favs = new ArrayList<>();
                try{
                    String columnName = MovieContract.FavoritesEntry.COLUMN_MOVIE_ID;
                    int columnIndex = c.getColumnIndex(columnName);
                    while(c.moveToNext()){
                        int favId = c.getInt(columnIndex);

                        Movie m = ma.getMovie(favId, apiKey).execute().body();
                        favs.add(m);
                    }
                    favMovies.setResults(favs);
                    c.close();
                }catch(IOException ioex){
                    ioex.printStackTrace();
                    return ioex;
                }
                return favMovies;
            }

            @Override
            protected void onPostExecute(Object o) {
                setChanged();
                notifyObservers(o);
            }
        }.execute();
    }
}