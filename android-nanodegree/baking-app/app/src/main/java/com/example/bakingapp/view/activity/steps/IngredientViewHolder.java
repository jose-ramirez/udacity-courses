package com.example.bakingapp.view.activity.steps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

/**
 * Created by jose on 10/12/17.
 */

public class IngredientViewHolder extends RecyclerView.ViewHolder{

    private Ingredient ingredient;

    public IngredientViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Ingredient ingredient){
        this.ingredient = ingredient;
        TextView tvDescription = (TextView) itemView.findViewById(R.id.ingredient_text);
        tvDescription.setText(String.format("%s (%.1f %s)",
                this.ingredient.getIngredient(),
                this.ingredient.getQuantity(),
                this.ingredient.getMeasure()));
    }
}
