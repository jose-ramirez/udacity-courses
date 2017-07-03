package com.example.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.data.Recipe;

import java.util.List;

/**
 * Created by jose on 23/05/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipe = inflater.inflate(R.layout.item, parent, false);
        return new RecipeViewHolder(recipe);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        if(recipes == null){
            return 0;
        }else{
            return recipes.size();
        }
    }
}
