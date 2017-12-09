package com.example.bakingapp.view.activity.steps;

import android.app.Application;
import android.app.FragmentManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.util.BakingAppUtil;
import com.example.bakingapp.view.activity.ListItemClickListener;
import com.example.bakingapp.view.activity.StepDetailsActivity;
import com.example.bakingapp.view.fragment.VideoPlayerFragment;
import com.example.bakingapp.view.widget.BakingAppWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements ListItemClickListener{

    @BindView(R.id.rv_recipe_steps) RecyclerView rvRecipeSteps;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.lv_ingredients) ListView ingredientsListView;

    private Recipe recipe;

    private static final String RECIPE_KEY = "recipe";

    private static final String STEP_KEY = "step";

    private static final int DEFAULT_SMALLEST_WIDTH = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_KEY);

        ButterKnife.bind(this);

        List<Step> steps = recipe.getSteps();
        StepsAdapter adapter = new StepsAdapter(steps, this);

        rvRecipeSteps.setLayoutManager(new LinearLayoutManager(this));
        rvRecipeSteps.setAdapter(adapter);

        ingredientsListView.setAdapter(
                new IngredientsAdapter(this, getIngredientStrings(recipe)));

        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);
    }

    private List<String> getIngredientStrings(Recipe recipe){
        List<String> ingredientStrings = new ArrayList<>();
        for(Ingredient i : recipe.getIngredients()){
            ingredientStrings.add(String.format("%s (%.1f %s)",
                    i.getIngredient(), i.getQuantity(), i.getMeasure()));
        }
        return ingredientStrings;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_widget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_add_to_widget){
            //save recipe id
            saveRecipeId();
            // send intent to widget, to update itself
            updateWidgets();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRecipeId(){
        SharedPreferences prefs = this.getApplicationContext()
                .getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("recipe_id", this.recipe.getId());
        editor.commit();
    }

    private void updateWidgets(){
        Application app = getApplication();
        Intent widgetUpdateIntent = new Intent(app, BakingAppWidget.class);
        widgetUpdateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager manager = AppWidgetManager.getInstance(this.getBaseContext());
        ComponentName provider = new ComponentName(app, BakingAppWidget.class);
        int[] appWidgetIds = manager.getAppWidgetIds(provider);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        app.sendBroadcast(widgetUpdateIntent);
        manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredients);
    }
}
