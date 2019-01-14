package com.example.bakingapp.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.service.BakingAppIngredientsRemoteService;
import com.example.bakingapp.util.DBUtils;
import com.example.bakingapp.view.activity.recipes.RecipesActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        long recipeId = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong("recipe_id", -1L);
        Recipe recipe = DBUtils.getRecipe(context.getContentResolver(), (int)recipeId);
        views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
        views.setRemoteAdapter(R.id.ingredients, new Intent(context, BakingAppIngredientsRemoteService.class));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}