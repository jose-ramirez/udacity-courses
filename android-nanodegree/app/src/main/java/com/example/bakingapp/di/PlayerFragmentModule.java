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
public class PlayerFragmentModule {

    private Context context;

    private Step step;

    private long lastPosition;

    public PlayerFragmentModule(Context context, Step step, long lastPosition){
        this.context = context;
        this.step = step;
        this.lastPosition = lastPosition;
    }

    /**
     *
     * @return
     */
    @Provides
    public Context context(){
        return this.context;
    }

    /**
     * Not like I needed this, but I don't believe it's a problem to leave it here.
     *
     * It returns a parameter needed for creating the player instance.
     *
     * @return A user agent string needed to create the player.
     */
    @Provides
    @Singleton
    public String provideUserAgent(){
        return Util.getUserAgent(this.context, "BakingApp");
    }

    /**
     * Just providing a pretty default ExoPlayer instance.
     *
     * It tries to depend on some state though, i.e., it attempts to start playback
     * from where it left; like, if I rotate the device halfway through, this player
     * should be capable of resuming the video from the last position of the video
     * before the device was rotated.
     *
     * But, since it became too much of a hassle to save the point in time when the
     * player was killed, I decided to ignore such capability (the video always
     * starts from the beginning, as lastPosition is always 0)
     * */
    @Provides
    @Singleton
    public SimpleExoPlayer providePlayer(String userAgent){
        DefaultTrackSelector selector = new DefaultTrackSelector();

        DefaultLoadControl loadControl = new DefaultLoadControl();

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this.context, selector, loadControl);

        DefaultDataSourceFactory defaultFactory = new DefaultDataSourceFactory(this.context, userAgent);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        String videoURL = this.step.getVideoURL();
        if(videoURL != null && !videoURL.isEmpty()){
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(this.step.getVideoURL()),
                    defaultFactory,
                    extractorsFactory, null, null);

            int resumeWindow = player.getCurrentWindowIndex();
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition && this.lastPosition >= 0) {
                player.seekTo(resumeWindow, this.lastPosition);
            }
            player.prepare(mediaSource, !haveResumePosition, false);
        }

        return player;
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
