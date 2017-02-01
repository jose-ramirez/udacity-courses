package com.ex.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ex.popularmovies.common.MovieGetter;
import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements Observer{

    TextView tvReleaseDate;

    TextView tvVoteAverage;

    TextView tvOriginalTitle;

    TextView tvSynopsis;

    ProgressBar pbLoading;

    TextView tvMovieDetailsErrorMessage;

    ScrollView svDetails;

    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.svDetails = (ScrollView) findViewById(R.id.sv_details);

        this.pbLoading = (ProgressBar) findViewById(R.id.pb_loading_movie_details);

        this.tvMovieDetailsErrorMessage = (TextView) findViewById(R.id.tv_movie_details_error_message);

        this.tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);

        this.tvOriginalTitle = (TextView) findViewById(R.id.tv_original_title);

        this.tvVoteAverage = (TextView) findViewById(R.id.tv_vote_average);

        this.tvSynopsis = (TextView) findViewById(R.id.tv_synopsis);

        this.ivPoster = (ImageView) findViewById(R.id.iv_poster);

        int movieId = getIntent().getIntExtra("movie_id", 550);

        new MovieGetter(this).getMovie(movieId);
    }

    @Override
    public void update(Observable observable, Object o) {

        if(o instanceof Response){
            showDetailsView();

            showMovieDetails((Response) o);

        }else if (o instanceof SocketTimeoutException){
            displayErrorMessage(this.getString(R.string.timeout_error_message));

        }else{
            displayErrorMessage("Unknown error:\n" + o.getClass().getSimpleName());
        }
    }

    private void showDetailsView() {
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.tvMovieDetailsErrorMessage.setVisibility(View.INVISIBLE);
        this.svDetails.setVisibility(View.VISIBLE);
    }

    private void showMovieDetails(Response res) {
        Movie m = (Movie) res.body();

        this.tvReleaseDate.setText(
                new SimpleDateFormat("dd/MM/yyyy").format(m.getReleaseDate()));

        this.tvOriginalTitle.setText(m.getOriginalTitle());

        this.tvVoteAverage.setText(
                String.format("%.2f (%d votes)", m.getVoteAvg(), m.getVotes()));

        this.tvSynopsis.setText(m.getSynopsis());

        Picasso
            .with(this)
            .load(this.getString(R.string.movie_detail_poster_base_url) + m.getPosterPath())
            .into(this.ivPoster);
    }

    private void displayErrorMessage(String msg){
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.svDetails.setVisibility(View.INVISIBLE);
        this.tvMovieDetailsErrorMessage.setText(msg);
        this.tvMovieDetailsErrorMessage.setVisibility(View.VISIBLE);
    }
}