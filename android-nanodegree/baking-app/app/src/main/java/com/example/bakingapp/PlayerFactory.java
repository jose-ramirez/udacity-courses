package com.example.bakingapp;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
/**
 * Created by jose on 26/12/17.
 */

public class PlayerFactory {

    private Context context;
    private String videoUrl;

    public PlayerFactory(Context context, String videoUrl){
        this.context = context;
        this.videoUrl = videoUrl;
    }

    public String provideUserAgent(){
        return Util.getUserAgent(this.context, "BakingApp");
    }

    public SimpleExoPlayer providePlayer(
            DefaultTrackSelector selector,
            DefaultLoadControl loadControl,
            MediaSource mediaSource){
        return ExoPlayerFactory.newSimpleInstance(this.context, selector, loadControl);
    }

    public DefaultDataSourceFactory provideDataSourceFactory(String userAgent){
        return new DefaultDataSourceFactory(this.context, provideUserAgent());
    }

    public DefaultLoadControl provideLoadControl(){
        return new DefaultLoadControl();
    }

    public DefaultTrackSelector provideTrackSelector(){
        return new DefaultTrackSelector();
    }

    public DefaultExtractorsFactory provideExtractorsFactory(){
        return new DefaultExtractorsFactory();
    }

    public MediaSource provideMediaSource(){
        return new ExtractorMediaSource(
                Uri.parse(this.videoUrl),
                provideDataSourceFactory(provideUserAgent()),
                provideExtractorsFactory(), null, null);
    }

    public SimpleExoPlayer player(){

        MediaSource mediaSource = provideMediaSource();

        return providePlayer(
                provideTrackSelector(),
                provideLoadControl(),
                mediaSource);
    }
}
