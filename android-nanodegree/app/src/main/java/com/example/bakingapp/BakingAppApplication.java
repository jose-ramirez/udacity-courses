package com.example.bakingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import io.reactivex.android.*;

import timber.log.Timber;

public class BakingAppApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // This is for use in the debug build variant only
        if(BuildConfig.DEBUG){
            // to detect memory leaks
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);

            // to analyze the app's database using the browser
            Stetho.initializeWithDefaults(this);
        }

        //Planting a Timber tree, for easier logging :)
        Timber.plant(new Timber.DebugTree());
    }
}
