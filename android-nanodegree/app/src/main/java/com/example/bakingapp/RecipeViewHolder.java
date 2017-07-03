package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapp.data.Recipe;

/**
 * Created by jose on 23/05/17.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvTitle;

    private Context context;

    private Recipe recipe;

    private static final String RECIPE_KEY = "recipe";

    public RecipeViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.tvTitle = (TextView) itemView.findViewById(R.id.tv_description);
        tvTitle.setOnClickListener(this);
    }

    public void bind(Recipe recipe){
        tvTitle.setText(recipe.getName());
        this.recipe = recipe;
    }

    @Override
    public void onClick(View v) {
        Intent stepsActivityIntent = new Intent(this.context, StepsActivity.class);
        stepsActivityIntent.putExtra(RECIPE_KEY, this.recipe);
        this.context.startActivity(stepsActivityIntent);
    }
}
