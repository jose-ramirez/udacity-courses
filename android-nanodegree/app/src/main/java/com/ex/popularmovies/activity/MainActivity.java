package com.ex.popularmovies.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ex.popularmovies.view.MovieAdapter;
import com.ex.popularmovies.common.MovieGetter;
import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Movies;

import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Observer{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView results;

    private MovieGetter mg;

    private ProgressBar pbLoading;

    private TextView tvErrorMessage;

    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
        this.mg.getMovies();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This will tell us to wait while loading the movie data:
        this.pbLoading = (ProgressBar) findViewById(R.id.pb_loading_movies);

        //Our error message will be here to show when something goes south:
        this.tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);

        //Creating/Configuring the recycler view to display the results in a grid:
        this.results = (RecyclerView) findViewById(R.id.rv_results_view);
        int orientation = getResources().getConfiguration().orientation;
        int cols = orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        GridLayoutManager layout = new GridLayoutManager(this, cols);
        this.results.setLayoutManager(layout);

        //Our movie getter (by default it attempts to get the list of popular movies first):
        this.mg = new MovieGetter(this);
        this.mg.getMovies();
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

        if(o instanceof Response){

            showResultsView();
            //got some movie data, yay!
            showMovieData((Response) o);

        }else if(o instanceof SocketTimeoutException){
            /*
                Couldn't connect after 5 seconds,
                or it connected, but it's taking more
                than 5 seconds to get the movie data.
            */
            displayErrorMessage(this.getString(R.string.timeout_error_message));

        }else{
            //Something went south here.
            displayErrorMessage("Uknown error occured:\n" + clazz);
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

    private void showLoading(){
        this.tvErrorMessage.setVisibility(View.INVISIBLE);
        this.results.setVisibility(View.INVISIBLE);
        this.pbLoading.setVisibility(View.VISIBLE);
    }

    private void showMovieData(Response res){
        Movies movs = (Movies)res.body();
        if(movs != null){
            this.results.setVisibility(View.VISIBLE);
            this.results.setAdapter(new MovieAdapter(movs));
        }else{
            this.tvErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}
