package com.ex.popularmovies.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ex.popularmovies.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
