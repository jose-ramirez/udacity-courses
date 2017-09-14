package com.example.bakingapp.db;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.provider.BakingAppProvider;
import com.example.bakingapp.util.DBTestUtils;
import com.example.bakingapp.util.DBUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by jose on 25/07/17.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private final Context context = InstrumentationRegistry.getTargetContext();
    private Uri recipesUri = BakingAppProvider.Recipes.CONTENT_URI;
    private Uri ingredientsUri = BakingAppProvider.Ingredients.CONTENT_URI;
    private Uri stepsUri = BakingAppProvider.Steps.CONTENT_URI;
    private Recipe recipe;

    @Before
    public void setup(){
        this.recipe = DBTestUtils.createFakeRecipe();
    }

    /**
     * It should verify that the provider was correctly registered.
     */
    @Test
    public void providerIsRegisteredCorrectly(){
        String packageName = context.getPackageName();
        String providerClassName = "com.example.bakingapp.generated.BakingAppProvider";
        ComponentName componentName = new ComponentName(packageName, providerClassName);

        try{
            PackageManager pm = context.getPackageManager();

            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = BakingAppProvider.AUTHORITY;

            assertEquals(actualAuthority, expectedAuthority);

        }catch (PackageManager.NameNotFoundException nnfe){
            fail(nnfe.getMessage());
        }
    }

    /**
     * It should save the recipe in the db.
     */
    @Test
    public void testDatabaseCreatesRecipes(){

        // Create the ContentValues object
        ContentValues recipeValues = DBUtils.getRecipeContentValue(this.recipe);

        // Inserting the recipe into the db
        context.getContentResolver().insert(recipesUri, recipeValues);

        // Verifying the recipe was inserted
        Cursor recipesCursor = context.getContentResolver().query(recipesUri, null, null, null, null);
        assertTrue(recipesCursor.getCount() == 1);
    }

    @After
    public void tearDown(){
        // Clean the whole database
        context.getContentResolver().delete(recipesUri, null, null);
        context.getContentResolver().delete(ingredientsUri, null, null);
        context.getContentResolver().delete(stepsUri, null, null);
    }
}
