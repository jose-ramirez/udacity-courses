package com.example.bakingapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by jose on 05/07/17.
 */

@Database(
        version = BakingAppDatabase.VERSION,
        packageName = "com.example.bakingapp.generated")
public class BakingAppDatabase {

    public static final int VERSION = 1;

    public static class Tables{

        @Table(RecipeColumns.class)
        @IfNotExists
        public static final String RECIPES = "recipes";

        @Table(StepColumns.class)
        @IfNotExists
        public static final String STEPS = "steps";

        @Table(IngredientColumns.class)
        @IfNotExists
        public static final String INGREDIENTS = "ingredients";
    }

    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + Tables.RECIPES;

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {}

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion) {}

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {}
}
