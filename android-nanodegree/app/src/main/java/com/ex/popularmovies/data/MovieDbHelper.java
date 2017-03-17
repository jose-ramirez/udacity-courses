package com.ex.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jose on 15/03/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "movies.db";

    public static final int DB_VERSION = 2;

    public MovieDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format("create table %s (" +
                "%s integer not null, " +
                "%s text not null);",
                MovieContract.FavoritesEntry.TABLE_NAME,
                MovieContract.FavoritesEntry.COLUMN_MOVIE_ID,
                MovieContract.FavoritesEntry.COLUMN_MOVIE_TITLE);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(String.format("drop table if exists %s;",
                MovieContract.FavoritesEntry.TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
}
