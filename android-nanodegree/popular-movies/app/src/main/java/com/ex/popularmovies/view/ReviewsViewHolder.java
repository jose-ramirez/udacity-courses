package com.ex.popularmovies.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Review;

/**
 * Created by jose on 15/03/17.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAuthor;
    private TextView tvContent;
    private TextView tvUrl;

    public ReviewsViewHolder(View itemView) {
        super(itemView);
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
        tvContent = (TextView) itemView.findViewById(R.id.tv_review_content);
        tvUrl = (TextView) itemView.findViewById(R.id.tv_review_url);
    }

    public void bind(Review r){
        tvContent.setText(r.getContent());
        tvAuthor.setText(r.getAuthor());
        tvUrl.setText(r.getUrl());
    }
}
