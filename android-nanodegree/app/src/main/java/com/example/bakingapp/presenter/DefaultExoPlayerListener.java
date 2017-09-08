package com.example.bakingapp.presenter;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.Locale;

import timber.log.Timber;

/**
 * A listener for ExoPlayer events.
 *
 * For the time being, it only logs any detected change.
 * */

public class DefaultExoPlayerListener implements ExoPlayer.EventListener {
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        Timber.i("Timeline changed, somehow");
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        Timber.i("Tracks changed");
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Timber.i("Loading changed (isLoading): " + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Timber.i(String.format(Locale.getDefault(), "Player state changed: (%b, %d)", playWhenReady, playbackState));
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Timber.i(String.format(Locale.getDefault(), "Some error ocurred: %s", error.toString()));
    }

    @Override
    public void onPositionDiscontinuity() {
        Timber.i("Discontinuity ocurred");
    }
}
