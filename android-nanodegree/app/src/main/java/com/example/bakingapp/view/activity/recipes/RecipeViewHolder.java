package com.example.bakingapp.view.activity.recipes;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.util.DBUtils;
import com.example.bakingapp.view.activity.steps.StepsActivity;

/**
 * Created by jose on 23/05/17.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvTitle;

    private Context context;

    private ImageButton favButton;

    private Recipe recipe;

    private static final String RECIPE_KEY = "recipe";

    public RecipeViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.tvTitle = (TextView) itemView.findViewById(R.id.tv_description);
        this.favButton = (ImageButton) itemView.findViewById(R.id.fav_button);
        tvTitle.setOnClickListener(this);
    }

    public void bind(Recipe recipe){
        tvTitle.setText(recipe.getName());
        this.recipe = recipe;
        ContentResolver provider = this.context.getContentResolver();
        this.favButton.setSelected(DBUtils.isFavorite(provider, recipe));
        this.favButton.setTag(this.recipe);
    }

    @Override
    public void onClick(View v) {
        Intent stepsActivityIntent = new Intent(this.context, StepsActivity.class);
        stepsActivityIntent.putExtra(RECIPE_KEY, this.recipe);
        this.context.startActivity(stepsActivityIntent);
    }
}
