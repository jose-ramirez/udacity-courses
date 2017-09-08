package com.example.bakingapp.db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;
import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;


/**
 * Created by jose on 05/07/17.
 */

public interface IngredientColumns {

    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String ID = "_id";

    @DataType(INTEGER)
    @References(table = "recipes", column = RecipeColumns.ID)
    String RECIPE_ID = "recipe_id";

    @DataType(TEXT)
    @NotNull
    String QUANTITY = "quantity";

    @DataType(TEXT)
    @NotNull
    String MEASURE = "measure";

    @DataType(TEXT)
    @NotNull
    String INGREDIENT = "ingredient";
}