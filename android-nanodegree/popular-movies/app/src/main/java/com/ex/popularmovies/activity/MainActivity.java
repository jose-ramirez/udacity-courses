package com.ex.popularmovies.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ex.popularmovies.R;
import com.ex.popularmovies.api.MovieGetter;
import com.ex.popularmovies.models.Movies;
import com.ex.popularmovies.view.MovieAdapter;
import com.facebook.stetho.Stetho;

import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Observer,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MovieGetter mg;

    private SharedPreferences settings;

    @BindView(R.id.rv_results_view) RecyclerView results;

    //This will tell us to wait while loading the movie data:
    @BindView(R.id.pb_loading_movies) ProgressBar pbLoading;

    //Our error message will be here to show when something goes south:
    @BindView(R.id.tv_error_message) TextView tvErrorMessage;

    @Override
    protected void onResume() {
        super.onResume();
        updateView(getString(R.string.sort_by_key));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //we need to make this activity listen to preference changes.
        this.settings = PreferenceManager.getDefaultSharedPreferences(this);
        this.settings.registerOnSharedPreferenceChangeListener(this);

        //the movies will be shown in a grid, the number of columns depending
        //on the device's orientation.
        int orientation = getResources().getConfiguration().orientation;
        int cols = orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        GridLayoutManager layout = new GridLayoutManager(this, cols);
        this.results.setLayoutManager(layout);

        //Our movie getter. Queries the movie database based on the sort criteria
        //that was last set in the settings activity.
        this.mg = new MovieGetter(this);
        updateView(getString(R.string.sort_by_key));
    }

    private void updateView(String key) {
        showLoadingProgressBar();
        String sortBy = this.settings.getString(key,
                getString(R.string.default_sort_criteria));
        if(!getString(R.string.sort_favorites).equals(sortBy)){
            this.mg.getMovies();
        }else{
            this.mg.getFavorites();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o) {

        String clazz = o.getClass().getSimpleName();

        if(o instanceof Movies){

            //got some movie data, yay!
            showMovieData((Movies) o);

        }else if(o instanceof SocketTimeoutException){
            /*
                It enters here if it couldn't connect after 5 seconds,
                or if it connected, but it's taking more than 5 seconds
                to get the movie data.
            */
            displayErrorMessage(this.getString(R.string.timeout_error_message));

        }else{
            //Something unexpected went south here.
            displayErrorMessage("Uknown error:\n" + clazz);
        }
    }

    private void showResultsView() {
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.tvErrorMessage.setVisibility(View.INVISIBLE);
        this.results.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage(String msg){
        this.results.setVisibility(View.INVISIBLE);
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.tvErrorMessage.setText(msg);
        this.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showLoadingProgressBar(){
        this.tvErrorMessage.setVisibility(View.INVISIBLE);
        this.results.setVisibility(View.INVISIBLE);
        this.pbLoading.setVisibility(View.VISIBLE);
    }

    private void showMovieData(Movies movs){
        showResultsView();
        if(movs != null){
            this.results.setVisibility(View.VISIBLE);
            this.results.setAdapter(new MovieAdapter(movs));
        }else{
            this.tvErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    /*
    *   Here we want to listen to preference changes, so we
    *   can update the movie list shown on the main activity
    *   based on the currently selected sort criteria (most
    *   popular first, top rated first, or favorites).
    * */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateView(key);
    }
}