package com.example.bakingapp.di;

/*
  Created by jose on 25/05/17.
 */

import android.content.Context;

import com.example.bakingapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={MainActivityModule.class})
public interface MainActivityComponent {

    Context context();

    void inject(MainActivity activity);
}
