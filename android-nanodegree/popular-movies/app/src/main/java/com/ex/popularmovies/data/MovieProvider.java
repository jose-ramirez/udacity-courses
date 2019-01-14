package com.ex.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jose on 15/03/17.
 */

public class MovieProvider extends ContentProvider {

    private MovieDbHelper dbHelper;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static final int FAVORITE = 100;
    private static final int FAVORITE_WITH_ID = 101;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.FAVORITES_PATH, FAVORITE);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.FAVORITES_PATH + "/#", FAVORITE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        this.dbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projections, @Nullable String selections, @Nullable String[] selectionArgs, @Nullable String orderBy) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor resultSet = null;
        switch(sUriMatcher.match(uri)){
            case FAVORITE:
                resultSet = db.query(
                        MovieContract.FavoritesEntry.TABLE_NAME,
                        projections,
                        selections,
                        selectionArgs,
                        null,
                        null,
                        orderBy);
                break;
            case FAVORITE_WITH_ID:
                String id = uri.getLastPathSegment();
                resultSet = db.query(
                        MovieContract.FavoritesEntry.TABLE_NAME,
                        projections,
                        "id=?",
                        new String[]{id},
                        null,
                        null,
                        orderBy);
                break;
            default:
                throw new RuntimeException("Unknown URI: " + uri);
        }
        return resultSet;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        Uri res = null;
        switch(sUriMatcher.match(uri)){
            case FAVORITE:
                long id = db.insert(MovieContract.FavoritesEntry.TABLE_NAME, null, contentValues);
                if(id > 0){
                    res = ContentUris.withAppendedId(MovieContract.FavoritesEntry.CONTENT_URI, id);
                }break;
            default:
                throw new RuntimeException("Unknown URI: " + uri);
        }db.close();
        return res;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriMatch = sUriMatcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows;
        switch(uriMatch){
            case FAVORITE:
                deletedRows = db.delete(MovieContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new RuntimeException("Unknown URI: " + uri.toString());
        }
        if(deletedRows > 0){
            getContext().getContentResolver().notifyChange(uri, null);
            return deletedRows;
        }db.close();
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
