package com.example.bakingapp.view.activity.player;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.di.DaggerPlayerComponent;
import com.example.bakingapp.di.PlayerModule;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.presenter.HeadphonePluggedDetector;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepVideoPlayerActivity extends Activity implements ExoPlayer.EventListener{

    private static final String STEP_KEY = "step";

    private static final String LAST_POSITION_KEY = "lastPosition";

    private static final String LAST_STATE_KEY = "lastState";

    private Step step;

    private String videoUrl;

    private long lastPosition;

    private boolean isPlaying;

    @Inject HeadphonePluggedDetector headsetReceiver;

    @Inject IntentFilter filter;

    @Inject SimpleExoPlayer player;

    @Inject MediaSource mediaSource;

    @Nullable @BindView(R.id.tv_step_description) TextView tvStepDescription;

    @BindView(R.id.recipe_step_player_view) SimpleExoPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_video_player);

        if(savedInstanceState != null){
            this.lastPosition = savedInstanceState.getLong(LAST_POSITION_KEY);
            this.isPlaying = savedInstanceState.getBoolean(LAST_STATE_KEY);
        }else{
            this.isPlaying = true;
            this.lastPosition = 0;
        }

        ButterKnife.bind(this);

        this.step = (Step) getIntent().getSerializableExtra(STEP_KEY);

        this.videoUrl = this.step.getVideoURL();

        PlayerModule pm = new PlayerModule(this, this.videoUrl);

        DaggerPlayerComponent.builder()
                .playerModule(pm)
                .build()
                .inject(this);

        this.playerView.setPlayer(this.player);

        this.player.addListener(this);

        showMessageIfEmptyUrl();

        showStepDescription();

        skipTo(this.lastPosition);
    }

    private void skipTo(long position){
        if(this.videoUrl != null && !this.videoUrl.isEmpty()){
            int resumeWindow = this.player.getCurrentWindowIndex();
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition && position > 0) {
                player.seekTo(resumeWindow, position);
            }
            player.prepare(this.mediaSource, !haveResumePosition, false);

        }
    }

    private void showStepDescription(){
        if (tvStepDescription != null) {
            tvStepDescription.setText(this.step.getDescription());
        }
    }

    private void showMessageIfEmptyUrl(){
        String videoURL = this.step.getVideoURL();
        String emptyUrlMsg = getResources().getString(R.string.invalid_video_url_msg);
        if(videoURL == null || videoURL.isEmpty()){
            Toast.makeText(this, emptyUrlMsg, Toast.LENGTH_SHORT).show();
        }
    }

    public void releasePlayer(){
        if(this.player != null){
            this.lastPosition = this.player.getCurrentPosition();
            this.player.stop();
            this.player.release();
            this.player = null;
        }
        if(this.playerView != null){
            this.playerView.setPlayer(null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(this.player != null){
            this.player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(this.headsetReceiver != null){
            this.unregisterReceiver(this.headsetReceiver);
        }
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.headsetReceiver != null && this.filter != null){
            this.registerReceiver(this.headsetReceiver, this.filter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(LAST_POSITION_KEY, this.lastPosition);
        outState.putBoolean(LAST_STATE_KEY, this.isPlaying);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == Player.STATE_READY){
            this.player.setPlayWhenReady(this.isPlaying);
            this.isPlaying = playWhenReady;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
