package com.ex.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Review;

import java.util.List;

/**
 * Created by jose on 15/03/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    private final List<Review> results;

    public ReviewsAdapter(List<Review> results) {
        this.results = results;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView = inflater.inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
