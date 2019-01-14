package com.example.bakingapp.view.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.util.DBUtils;

/**
 * Created by jose on 16/11/17.
 */

public class BakingAppIngredientsRemoteFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private Cursor mCursor;
    private Recipe recipe;

    public BakingAppIngredientsRemoteFactory(Context applicationContext, Intent intent) {
        this.mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {
        int recipeId = (int)this.mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong("recipe_id", -1L);
        this.recipe = DBUtils.getRecipe(mContext.getContentResolver(), recipeId);
    }

    @Override
    public int getCount() {
        return this.recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(this.mContext.getPackageName(), R.layout.widget_ingredient);
        Ingredient ingredient = this.recipe.getIngredients().get(position);

        view.setTextViewText(R.id.recipe_ingredient_name, ingredient.getIngredient());
        view.setTextViewText(R.id.recipe_ingredient_quantity,
                String.format("%s %s", ingredient.getQuantity(), ingredient.getMeasure()));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
