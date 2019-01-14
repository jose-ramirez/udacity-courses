package com.example.bakingapp.db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by jose on 05/07/17.
 */

public interface RecipeColumns {

    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String ID = "_id";

    @DataType(TEXT)
    @NotNull
    String NAME = "name";

    @DataType(TEXT)
    @NotNull
    String SERVINGS = "servings";

    @DataType(TEXT)
    @NotNull
    String IMAGE = "image";

    @DataType(INTEGER)
    @NotNull
    String FAVORITE = "favorite";
}
