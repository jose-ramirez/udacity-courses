package com.ex.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jose on 15/03/17.
 */

public class MovieContract {

    public static String AUTHORITY = "com.ex.popularmovies";

    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String FAVORITES_PATH = "movies";

    public static class FavoritesEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(FAVORITES_PATH).build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_MOVIE_ID = "id";

        public static final String COLUMN_MOVIE_TITLE = "title";
    }
}
