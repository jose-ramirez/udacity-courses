package com.example.bakingapp.util;

import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 13/09/17.
 */

public class DBTestUtils {

    /**
     * It just creates a fake recipe, to test the database code.
     *
     * @return A fake recipe.
     */
    public static Recipe createFakeRecipe(){
        // The recipe:
        Recipe r = new Recipe();

        // The ingredients:
        List<Ingredient> ings = new ArrayList<>();
        Ingredient i = new Ingredient();
        i.setIngredient("ingre");
        i.setMeasure("asd");
        i.setQuantity(1.0);

        // The steps:
        List<Step> steps = new ArrayList<Step>();
        Step s = new Step();
        s.setId(1L);
        s.setDescription("step description");
        s.setShortDescription("step short description");
        s.setThumbnailURL("thumbnail URL");
        s.setVideoURL("video URL");

        // Building the recipe:
        r.setId(1L);
        r.setServings(1L);
        r.setImage("some image URL");
        r.setName("Recipe name");
        r.setSteps(steps);
        r.setIngredients(ings);

        return r;
    }
}
