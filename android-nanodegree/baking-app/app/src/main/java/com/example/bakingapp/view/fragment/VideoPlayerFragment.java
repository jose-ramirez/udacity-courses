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

import com.example.bakingapp.PlayerFactory;
import com.example.bakingapp.R;
import com.example.bakingapp.di.DaggerPlayerComponent;
import com.example.bakingapp.di.PlayerModule;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.presenter.HeadphonePluggedDetector;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayerFragment extends Fragment{

    private static final String STEP_KEY = "step";
    private Step step;
    private String videoUrl;
    //@Inject HeadphonePluggedDetector headsetReceiver;
    //@Inject SimpleExoPlayer player;
    //@Inject MediaSource mediaSource;
    @Inject IntentFilter filter;
    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    private HeadphonePluggedDetector headsetReceiver;
    @Nullable @BindView(R.id.tv_step_description) TextView tvStepDescription;
    @BindView(R.id.recipe_step_player_view) SimpleExoPlayerView playerView;

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
            this.step = (Step) getArguments().getSerializable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        ButterKnife.bind(this, view);
        this.videoUrl = this.step.getVideoURL();
        PlayerModule pm = new PlayerModule(getActivity(), this.videoUrl);
        DaggerPlayerComponent.builder().playerModule(pm).build().inject(this);

        this.player = new PlayerFactory(this.getContext(), this.videoUrl).player();
        this.playerView.setPlayer(player);
        showMessageIfEmptyUrl();
        showStepDescription();
        skipTo(0);
        return view;
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
            Toast.makeText(getContext(), emptyUrlMsg, Toast.LENGTH_SHORT).show();
        }
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
