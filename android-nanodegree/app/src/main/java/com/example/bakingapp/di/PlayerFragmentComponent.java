package com.example.bakingapp.di;

import android.content.Context;

import com.example.bakingapp.view.activity.steps.StepsActivity;
import com.example.bakingapp.view.fragment.VideoPlayerFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jose on 23/06/17.
 */
@Singleton
@Component(modules = {PlayerFragmentModule.class})
public interface PlayerFragmentComponent {

    Context context();

    void inject(StepsActivity activity);

    void inject(VideoPlayerFragment playerFragment);
}
