package com.ex.popularmovies.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ex.popularmovies.R;
import com.ex.popularmovies.api.MovieGetter;
import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Reviews;
import com.ex.popularmovies.models.Video;
import com.ex.popularmovies.models.Videos;
import com.ex.popularmovies.utils.Utils;
import com.ex.popularmovies.view.ReviewsAdapter;
import com.ex.popularmovies.view.VideosAdapter;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements Observer{

    @BindView(R.id.tv_release_date) TextView tvReleaseDate;

    @BindView(R.id.tv_vote_average) TextView tvVoteAverage;

    @BindView(R.id.tv_original_title) TextView tvOriginalTitle;

    @BindView(R.id.tv_synopsis) TextView tvSynopsis;

    @BindView(R.id.pb_loading_movie_details) ProgressBar pbLoading;

    @BindView(R.id.tv_movie_details_error_message) TextView tvMovieDetailsErrorMessage;

    @BindView(R.id.sv_details) ScrollView svDetails;

    @BindView(R.id.iv_poster) ImageView ivPoster;

    @BindView(R.id.rv_videos) RecyclerView rvVideos;

    @BindView(R.id.rv_reviews) RecyclerView rvReviews;

    @BindView(R.id.add_to_fav_button) Button favButton;

    private int movieId;

    private MovieGetter mg;

    private Movie mov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        LinearLayoutManager vidLlm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.rvVideos.setLayoutManager(vidLlm);

        LinearLayoutManager revLlm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        this.rvReviews.setLayoutManager(revLlm);

        this.movieId = getIntent().getIntExtra("movie_id", 550);

        this.mg = new MovieGetter(this);

        this.mg.getMovie(movieId);
    }

    @Override
    public void update(Observable observable, Object o) {

        String clazz = o.getClass().getSimpleName();
        if(o instanceof Movie){
            this.mov = (Movie) o;
            showMovieDetails((Movie) o);
            this.mg.getMovieVideos(this.movieId);
        }else if (o instanceof Videos) {
            Videos vids = (Videos) o;
            rvVideos.setAdapter(new VideosAdapter(vids.getResults()));
            new MovieGetter(this).getMovieReviews(this.movieId);
        }else if (o instanceof Reviews) {
            Reviews reviews = (Reviews) o;
            rvReviews.setAdapter(new ReviewsAdapter(reviews.getResults()));
        }else if (o instanceof SocketTimeoutException){
            displayErrorMessage(this.getString(R.string.timeout_error_message));
        }else{
            displayErrorMessage("Unknown error:\n" + clazz);
        }
    }

    private void showDetailsView() {
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.tvMovieDetailsErrorMessage.setVisibility(View.INVISIBLE);
        this.svDetails.setVisibility(View.VISIBLE);
    }

    private void showMovieDetails(Movie m) {
        showDetailsView();

        this.tvReleaseDate.setText(
                new SimpleDateFormat(
                        getString(R.string.date_format)).format(m.getReleaseDate()));

        this.tvOriginalTitle.setText(m.getOriginalTitle());

        String formatString = getString(R.string.vote_average_format_string).replace("%%", "%");
        this.tvVoteAverage.setText(
                String.format(formatString, m.getVoteAvg(), m.getVotes()));

        this.tvSynopsis.setText(m.getSynopsis());

        Picasso
            .with(this)
            .load(this.getString(R.string.movie_detail_poster_base_url) + m.getPosterPath())
            .into(this.ivPoster);

        if(Utils.isMovieFavorite(m.getId(), getContentResolver())){
            favButton.setText(R.string.unfav_movie_label);
        }
    }

    private void displayErrorMessage(String msg){
        this.pbLoading.setVisibility(View.INVISIBLE);
        this.svDetails.setVisibility(View.INVISIBLE);
        this.tvMovieDetailsErrorMessage.setText(msg);
        this.tvMovieDetailsErrorMessage.setVisibility(View.VISIBLE);
    }

    public void viewPossiblyOnYoutube(View view){
        Video v = (Video) view.getTag();
        String videoId = v.getKey();
        Utils.showVideoFromHere(videoId, this);
    }

    public void updateFavList(View view){
        if(mov != null){
            final int movieId = this.movieId;
            final Activity detailsActivity = this;
            Utils.updateFavorites(detailsActivity, movieId, mov.getTitle()).execute();
        }
    }
}