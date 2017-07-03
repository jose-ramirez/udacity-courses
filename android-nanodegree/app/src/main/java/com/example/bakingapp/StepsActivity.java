package com.example.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements ListItemClickListener{

    @BindView(R.id.rv_recipe_steps) RecyclerView rvRecipeSteps;

    private static final String RECIPE_KEY = "recipe";

    private static final String STEP_KEY = "step";

    private static final int DEFAULT_SMALLEST_WIDTH = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ButterKnife.bind(this);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_KEY);
        List<Step> steps = recipe.getSteps();
        StepsAdapter adapter = new StepsAdapter(steps, this);

        rvRecipeSteps.setLayoutManager(new LinearLayoutManager(this));
        rvRecipeSteps.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        Step step = (Step) v.getTag();
        if(!BakingAppUtil.sw(this, DEFAULT_SMALLEST_WIDTH)){
            Intent stepVideoIntent = new Intent(this, StepDetailsActivity.class);
            stepVideoIntent.putExtra(STEP_KEY, step);
            startActivity(stepVideoIntent);
        }else{
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.step_details_fragment_layout,
                            VideoPlayerFragment.newInstance(step))
                    .commit();
        }
    }
}
