package com.ex.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Video;

import java.util.List;

/**
 * Created by jose on 15/03/17.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosViewHolder>{
    private List<Video> vids;

    public VideosAdapter(List<Video> vids) {
        this.vids = vids;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View videoView = inflater.inflate(R.layout.video_item, parent, false);
        return new VideosViewHolder(videoView);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
        holder.bind(vids.get(position));
    }

    @Override
    public int getItemCount() {
        return vids.size();
    }
}
