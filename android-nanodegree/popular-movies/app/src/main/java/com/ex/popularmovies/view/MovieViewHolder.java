package com.ex.popularmovies.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ex.popularmovies.R;
import com.ex.popularmovies.activity.MovieDetailsActivity;
import com.ex.popularmovies.models.Movie;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView poster;

    private Movie mov;

    private View v;

    public ImageView getPoster() {
        return poster;
    }

    public void setMovie(Movie mov){
        this.mov = mov;
    }

    public MovieViewHolder(View itemView) {
        super(itemView);
        this.poster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        itemView.setOnClickListener(this);
    }

    /*
    * To show the movie details upon clicking the movie poster
    * on the main view.
    * */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", this.mov.getId());
        view.getContext().startActivity(intent);
    }
}
