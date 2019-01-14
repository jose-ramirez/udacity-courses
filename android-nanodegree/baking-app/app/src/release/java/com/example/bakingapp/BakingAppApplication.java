package com.example.bakingapp;

import android.app.Application;

import timber.log.Timber;

public class BakingAppApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        //Planting a Timber tree, for easier logging :)
        Timber.plant(new Timber.DebugTree());
    }
}
