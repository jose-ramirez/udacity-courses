package com.example.bakingapp.provider;

import android.net.Uri;

import com.example.bakingapp.db.BakingAppDatabase;
import com.example.bakingapp.db.BakingAppDatabase.Tables;
import com.example.bakingapp.db.IngredientColumns;
import com.example.bakingapp.db.RecipeColumns;
import com.example.bakingapp.db.StepColumns;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * This is basically the core of the contract for our database, defining
 * content URIs and such and their corresponding paths.
 */

@ContentProvider(
        authority = BakingAppProvider.AUTHORITY,
        database = BakingAppDatabase.class,
        packageName = "com.example.bakingapp.generated")
public class BakingAppProvider {

    public static final String AUTHORITY = "com.example.bakingapp.provider.BakingAppProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String RECIPES = "recipes";
        String INGREDIENTS = "ingredients";
        String STEPS = "steps";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    /**
     * Recipes endpoint for the provider
     */
    @TableEndpoint(table = Tables.RECIPES) public static class Recipes{
        @ContentUri(
                path = Path.RECIPES,
                type = "vnd.android.cursor.dir/recipe",
                defaultSort = RecipeColumns.NAME + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.RECIPES);
    }

    /**
     * Ingredients endpoint for the provider
     */
    @TableEndpoint(table = Tables.INGREDIENTS) public static class Ingredients{
        @ContentUri(
                path = Path.INGREDIENTS,
                type = "vnd.android.cursor.dir/ingredient",
                defaultSort = IngredientColumns.ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.INGREDIENTS);
    }

    /**
     * Recipe steps endpoint for the provider
     */
    @TableEndpoint(table = Tables.STEPS) public static class Steps{
        @ContentUri(
                path = Path.STEPS,
                type = "vnd.android.cursor.dir/step",
                defaultSort = StepColumns.ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.STEPS);
    }
}
