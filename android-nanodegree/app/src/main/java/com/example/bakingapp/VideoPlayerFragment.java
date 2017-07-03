package com.example.bakingapp;

import android.app.Fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.data.Step;
import com.example.bakingapp.di.DaggerPlayerComponent;
import com.example.bakingapp.di.PlayerModule;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayerFragment extends Fragment {

    private static final String STEP_KEY = "step";

    private Step step;

    @Inject HeadphonePluggedDetector headsetReceiver;

    @Inject IntentFilter filter;

    @Nullable @BindView(R.id.tv_step_description) TextView tvStepDescription;

    @BindView(R.id.recipe_step_player_view) SimpleExoPlayerView playerView;

    @Inject SimpleExoPlayer player;

    public VideoPlayerFragment(){}

    public static VideoPlayerFragment newInstance(Step step) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = (Step) getArguments().getSerializable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        ButterKnife.bind(this, view);
        PlayerModule pm = new PlayerModule(getActivity(), step, 0);
        DaggerPlayerComponent.builder()
                .playerModule(pm)
                .build()
                .inject(this);
        playerView.setPlayer(player);
        if (tvStepDescription != null) {
            tvStepDescription.setText(this.step.getDescription());
        }
        return view;
    }

    public void releasePlayer(){
        if(this.player != null){
            this.player.stop();
            this.player.release();
            this.player = null;
        }
    }

    @Override
    public void onStart() {
        if(this.player != null){
            this.player.setPlayWhenReady(true);
        }
        super.onStart();
    }

    @Override
    public void onPause() {
        if(this.headsetReceiver != null){
            getActivity().unregisterReceiver(this.headsetReceiver);
        }
        if(this.player != null){
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if(this.headsetReceiver != null && this.filter != null){
            getActivity().registerReceiver(this.headsetReceiver, this.filter);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        this.playerView.setPlayer(null);
        super.onStop();
    }
}
