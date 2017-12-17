package com.example.bakingapp.di;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.example.bakingapp.model.Step;
import com.example.bakingapp.presenter.HeadphonePluggedDetector;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jose on 23/06/17.
 */
@Module
public class PlayerModule {

    private Context context;

    private String videoUrl;

    public PlayerModule(Context context, String videoUrl){
        this.context = context;
        this.videoUrl = videoUrl;
    }

    @Provides
    public Context context(){
        return this.context;
    }

    @Provides
    @Singleton
    public String provideUserAgent(){
        return Util.getUserAgent(this.context, "BakingApp");
    }

    @Provides
    @Singleton
    public SimpleExoPlayer providePlayer(
            String userAgent,
            DefaultTrackSelector selector,
            DefaultLoadControl loadControl,
            MediaSource mediaSource){
        return ExoPlayerFactory.newSimpleInstance(this.context, selector, loadControl);
    }

    @Provides
    @Singleton
    public DefaultDataSourceFactory provideDataSourceFactory(String userAgent){
        return new DefaultDataSourceFactory(this.context, userAgent);
    }

    @Provides
    @Singleton
    public DefaultLoadControl provideLoadControl(){
        return new DefaultLoadControl();
    }

    @Provides
    @Singleton
    public DefaultTrackSelector provideTrackSelector(){
        return new DefaultTrackSelector();
    }

    @Provides
    @Singleton
    public DefaultExtractorsFactory provideExtractorsFactory(){
        return new DefaultExtractorsFactory();
    }

    @Provides
    @Singleton
    public MediaSource provideMediaSource(
            DefaultDataSourceFactory defaultFactory,
            DefaultExtractorsFactory extractorsFactory){
        return new ExtractorMediaSource(
                Uri.parse(this.videoUrl),
                defaultFactory,
                extractorsFactory, null, null);
    }

    /**
     * This one I like :)
     *
     * It works as a broadcast receiver for headset events; the idea here is that
     * if you're listening to some video with your headphones, and then unplug them
     * halfway through, the player should pause the video.
     *
     * @param player
     * @return The "headset plugged detector".
     */
    @Provides
    @Singleton
    public HeadphonePluggedDetector provideDetector(SimpleExoPlayer player){
        return new HeadphonePluggedDetector(player);
    }

    /**
     * This is so the headset plugged detector can work.
     *
     * @return an IntentFilter for headset events.
     */
    @Provides
    @Singleton
    public IntentFilter provideIntentFilter(){
        return new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    }
}
