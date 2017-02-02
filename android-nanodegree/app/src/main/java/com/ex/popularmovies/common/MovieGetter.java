package com.ex.popularmovies.common;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Movies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        this.apiKey = this.ctx.getString(R.string.api_key);


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

    /*
    * Gets a movie based on the ID passed to it, and notifies to anyone
    * listening about its results (whether it fetched a movie or not).
    * */
    public void getMovie(int id){
        Callback<Movie> movieCallback = new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setChanged();
                notifyObservers(response);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
                setChanged();
                notifyObservers(t);
            }
        };


        ma.getMovie(id, this.apiKey).enqueue(movieCallback);
    }

    /*
    * Gets a movie list based on the sort criteria specified in the settings:
    * most popular movies first, or top rated movies first. It also notifies
    * to any class listening about the results of the query, i.e., the list
    * of movies, or an exception.
    * */
    public void getMovies(){

        String criteria =
        PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(
                this.ctx.getString(R.string.sort_by_key), "popular");

        Callback<Movies> movieCallback = new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Log.i(LOG_TAG, response.raw().request().url().toString());
                setChanged();
                notifyObservers(response);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
                setChanged();
                notifyObservers(t);
            }
        };

        ma.getMovies(criteria, this.apiKey).enqueue(movieCallback);
    }
}
