package com.ex.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Movie;
import com.ex.popularmovies.models.Movies;
import com.squareup.picasso.Picasso;

/**
 * Created by jose on 29/01/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{

    private Movies movs;

    public MovieAdapter(Movies movs){
        this.movs = movs;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View movieContent = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(movieContent);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie mov = this.movs.getResults().get(position);
        holder.setMovie(mov);
        Context ctx = holder.itemView.getContext();
        Picasso
            .with(ctx)
            .load(ctx.getString(R.string.movie_poster_base_url) + mov.getPosterPath())
            .into(holder.getPoster());
    }

    @Override
    public int getItemCount() {
        return this.movs.getResults().size();
    }
}
