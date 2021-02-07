package com.ex.popularmovies.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ex.popularmovies.R;
import com.ex.popularmovies.models.Video;

/**
 * Created by jose on 15/03/17.
 */

public class VideosViewHolder extends RecyclerView.ViewHolder {

    private TextView tvVideoInfo;

    private ImageButton ibViewTrailer;

    public VideosViewHolder(View itemView) {
        super(itemView);
        this.tvVideoInfo = (TextView) itemView.findViewById(R.id.tv_video_info);
        this.ibViewTrailer = (ImageButton) itemView.findViewById(R.id.ib_view_possibly_on_youtube);
    }

    public void bind(Video video){
        this.ibViewTrailer.setTag(video);
        this.tvVideoInfo.setText(video.getName());
    }
}
