package com.example.bakingapp.view.activity;

import android.view.View;

public interface ListItemClickListener {
    /**
     * Used to update the UI when the user clicks on a view.
     *
     * It's used to delegate the view holder's item clicked
     * event to the parent activity, since this event could
     * be handled directly inside the view holder, but, I
     * thought it wasn't elegant enough (correct me if I'm
     * wrong).
     * */
    void onClick(View v);
}
