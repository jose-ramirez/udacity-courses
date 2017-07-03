package com.example.bakingapp.di;

import android.content.Context;

import com.example.bakingapp.StepsActivity;
import com.example.bakingapp.VideoPlayerFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jose on 23/06/17.
 */
@Singleton
@Component(modules = {PlayerModule.class})
public interface PlayerComponent {

    Context context();

    void inject(StepsActivity activity);

    void inject(VideoPlayerFragment playerFragment);
}
