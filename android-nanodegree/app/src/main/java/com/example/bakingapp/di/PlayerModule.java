package com.example.bakingapp.di;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.example.bakingapp.HeadphonePluggedDetector;
import com.example.bakingapp.data.Step;
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

    private Step step;

    private long lastPosition;

    public PlayerModule(Context context, Step step, long lastPosition){
        this.context = context;
        this.step = step;
        this.lastPosition = lastPosition;
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
    public SimpleExoPlayer providePlayer(String userAgent){
        DefaultTrackSelector selector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this.context, selector, loadControl);
        //player.addListener(new DefaultExoPlayerListener());
        DefaultDataSourceFactory defaultFactory = new DefaultDataSourceFactory(this.context, userAgent);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(this.step.getVideoURL()), defaultFactory,
                extractorsFactory, null, null);
        int resumeWindow = player.getCurrentWindowIndex();
        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition && this.lastPosition >= 0) {
            player.seekTo(resumeWindow, this.lastPosition);
        }
        player.prepare(mediaSource, !haveResumePosition, false);
        return player;
    }

    @Provides
    @Singleton
    public HeadphonePluggedDetector provideDetector(SimpleExoPlayer player){
        return new HeadphonePluggedDetector(player);
    }

    @Provides
    @Singleton
    public IntentFilter provideIntentFilter(){
        return new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    }
}
