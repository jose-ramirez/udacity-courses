package com.example.bakingapp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

import timber.log.Timber;

/**
 * Created by jose on 07/06/17.
 */

public class HeadphonePluggedDetector extends BroadcastReceiver{

    private SimpleExoPlayer player;

    public HeadphonePluggedDetector(SimpleExoPlayer player){
        this.player = player;
    }

    public HeadphonePluggedDetector(){}

    /**
     * What to do when the headset is unplugged?
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    //If unplugged, stop playback
                    Timber.i(context.getString(R.string.headset_unplugged_msg));
                    if(this.player != null && this.player.getPlaybackState() == ExoPlayer.STATE_READY && player.getPlayWhenReady()){
                        this.player.setPlayWhenReady(false);
                    }
                    break;
                case 1:
                    // Else keep playing
                    Timber.i(context.getString(R.string.headset_plugged_msg));
                    if(this.player != null && this.player.getPlaybackState() == ExoPlayer.STATE_READY){
                        this.player.setPlayWhenReady(true);
                    }
                    break;
                default:
                    Timber.i(context.getString(R.string.unknown_headset_state_msg) + state);
            }
        }
    }
}
