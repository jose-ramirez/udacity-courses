package com.example.bakingapp.view.activity.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by jose on 12/09/17.
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepView = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(stepView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(this.ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        if(this.ingredients != null){
            return this.ingredients.size();
        }else{
            return 0;
        }
    }
}
