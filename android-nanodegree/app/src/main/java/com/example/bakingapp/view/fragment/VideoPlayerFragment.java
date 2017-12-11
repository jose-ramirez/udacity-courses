package com.example.bakingapp.view.fragment;

import android.app.Fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.R;
import com.example.bakingapp.di.DaggerPlayerFragmentComponent;
import com.example.bakingapp.di.PlayerFragmentModule;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.presenter.HeadphonePluggedDetector;
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

        // The last parameter is for resuming playback, i.e., if I rotate the device,
        // the player should resume playback from where it was before rotating; but I
        // hardcode it to 0 since I couldn't send this value from the fragment to the
        // activity in a clean way :(
        PlayerFragmentModule pm = new PlayerFragmentModule(getActivity(), step, 0);
        DaggerPlayerFragmentComponent.builder()
                .playerFragmentModule(pm)
                .build()
                .inject(this);

        this.playerView.setPlayer(player);

        String videoURL = this.step.getVideoURL();
        if(videoURL == null || videoURL.isEmpty()){
            Toast.makeText(
                    getContext(),
                    getResources().getString(R.string.invalid_video_url_msg),
                    Toast.LENGTH_LONG)
                .show();
        }

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
            getActivity().unregisterReceiver(this.headsetReceiver);
        }
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.headsetReceiver != null && this.filter != null){
            getActivity().registerReceiver(this.headsetReceiver, this.filter);
        }
    }
}
