package com.example.bakingapp.view.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.view.fragment.VideoPlayerFragment;

public class StepDetailsActivity extends Activity {

    private static final String STEP_KEY = "step";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Step step = (Step) getIntent().getSerializableExtra(STEP_KEY);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.step_details_fragment_layout,
                        VideoPlayerFragment.newInstance(step))
                .commit();
    }
}