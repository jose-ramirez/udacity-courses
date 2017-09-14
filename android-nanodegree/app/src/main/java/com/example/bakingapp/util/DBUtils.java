package com.example.bakingapp.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.bakingapp.db.IngredientColumns;
import com.example.bakingapp.db.RecipeColumns;
import com.example.bakingapp.db.StepColumns;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.provider.BakingAppProvider;
import com.example.bakingapp.provider.BakingAppProvider.Ingredients;
import com.example.bakingapp.provider.BakingAppProvider.Recipes;
import com.example.bakingapp.provider.BakingAppProvider.Steps;

import java.util.List;

/**
 * Created by jose on 06/07/17.
 */

public class DBUtils {

    private DBUtils(){}

    /**
     * Returns a ContentValues object from the data provided by the recipe given as a parameter.
     * @param recipe A recipe to save as a favorite.
     * @return A ContentValues object to be saved to the db.
     */
    public static ContentValues getRecipeContentValue(Recipe recipe){
        ContentValues cvRecipe = new ContentValues();
        cvRecipe.put(RecipeColumns.ID, recipe.getId());
        cvRecipe.put(RecipeColumns.NAME, recipe.getName());
        cvRecipe.put(RecipeColumns.IMAGE, recipe.getImage());
        cvRecipe.put(RecipeColumns.SERVINGS, recipe.getServings());
        return cvRecipe;
    }

    /**
     * Marks a recipe as a favorite by saving it to the database.
     * @param provider We'll check the database with this object.
     * @param recipe The recipe we wat to save for lateras a favorite
     */
    public static void saveRecipeAsFavorite(ContentResolver provider, Recipe recipe){
        ContentValues cvRecipe = getRecipeContentValue(recipe);
        ContentValues[] cvIngredients = getIngredientsContentValuesFromRecipe(recipe);
        ContentValues[] cvSteps = getStepsContentValuesFromRecipe(recipe);

        provider.insert(Recipes.CONTENT_URI, cvRecipe);

        if(cvIngredients != null && cvIngredients.length > 0){
            provider.bulkInsert(Ingredients.CONTENT_URI, cvIngredients);
        }

        if(cvSteps != null && cvSteps.length > 0){
            provider.bulkInsert(Steps.CONTENT_URI, cvSteps);
        }
    }

    /**
     * Returns true if the recipe is already saved as a favorite, else it returns false.
     * @param provider We'll check the database with this object.
     * @param recipe The recipe we're looking for to see if it's already a favorite or not.
     * @return true iff the recipe is already a favorite.
     */
    public static boolean isFavorite(ContentResolver provider, Recipe recipe){
        String recipeName = recipe.getName();
        Cursor recipeCursor = provider.query(
                BakingAppProvider.Recipes.CONTENT_URI,
                null,
                RecipeColumns.NAME + " = ?",
                new String[]{recipeName},
                null,
                null);
        return recipeCursor != null && recipeCursor.moveToFirst();
    }

    /**
     * Un-favs a recipe.
     * @param provider We'll check the database with this object.
     * @param recipe The recipe we're removing.
     */
    public static void deleteRecipe(ContentResolver provider, Recipe recipe){
        String[] recipeId = new String[]{String.valueOf(recipe.getId())};
        provider.delete(
                BakingAppProvider.Recipes.CONTENT_URI,
                RecipeColumns.ID + " = ?",
                recipeId);
        provider.delete(
                BakingAppProvider.Ingredients.CONTENT_URI,
                IngredientColumns.RECIPE_ID + " = ?",
                recipeId);
        provider.delete(
                BakingAppProvider.Steps.CONTENT_URI,
                StepColumns.RECIPE_ID + " = ?",
                recipeId);
    }

    private static ContentValues[] getIngredientsContentValuesFromRecipe(Recipe recipe){
        List<Ingredient> ingredients = recipe.getIngredients();
        ContentValues[] cvIngredients = null;
        if(ingredients != null && !ingredients.isEmpty()){
            cvIngredients = new ContentValues[ingredients.size()];
            for(int i = 0; i < ingredients.size(); i++){
                Ingredient ingredient = ingredients.get(i);
                ContentValues cv = new ContentValues();
                cv.put(IngredientColumns.RECIPE_ID, recipe.getId());
                cv.put(IngredientColumns.INGREDIENT, ingredient.getIngredient());
                cv.put(IngredientColumns.QUANTITY, ingredient.getQuantity());
                cv.put(IngredientColumns.MEASURE, ingredient.getMeasure());
                cvIngredients[i] = cv;
            }
        }
        return cvIngredients;
    }

    private static ContentValues[] getStepsContentValuesFromRecipe(Recipe recipe){
        List<Step> steps = recipe.getSteps();
        ContentValues[] cvSteps = null;
        if(steps != null && !steps.isEmpty()){
            cvSteps = new ContentValues[steps.size()];
            for(int j = 0; j < steps.size(); j++){
                Step step = steps.get(j);
                ContentValues cv = new ContentValues();
                cv.put(StepColumns.RECIPE_ID, recipe.getId());
                cv.put(StepColumns.VIDEO_URL, step.getVideoURL());
                cv.put(StepColumns.DESCRIPTION, step.getDescription());
                cv.put(StepColumns.THUMBNAIL_URL, step.getThumbnailURL());
                cv.put(StepColumns.SHORT_DESCRIPTION, step.getShortDescription());
                cvSteps[j] = cv;
            }
        }
        return cvSteps;
    }
}
