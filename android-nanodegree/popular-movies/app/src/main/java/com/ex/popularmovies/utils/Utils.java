package com.ex.popularmovies.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ex.popularmovies.R;
import com.ex.popularmovies.data.MovieContract;

/**
 * Created by jose on 17/03/17.
 */

public class Utils {

    public static final  Uri favsUri = MovieContract.FavoritesEntry.CONTENT_URI;

    /**
     * Checks if a movie is already marked as a favorite in the app's list of favorites.
     *
     * @param movieId the id of the movie to be evaluated.
     * @param cr The ContentResolver we'll be using to read the list of favorites.
     * */
    public static boolean isMovieFavorite(int movieId, ContentResolver cr){
        Uri uriWithId = favsUri.buildUpon().appendPath(String.valueOf(movieId)).build();
        Cursor checkRepeated = cr.query(uriWithId, null, null, null, null);
        return checkRepeated.getCount() > 0;
    }

    /**
     * Shows a toast with a message. Apparently, this one can be run from anywhere (in this
     * case, from an {@link android.os.AsyncTask}) because it asks for the activity on which
     * the toast will be shown. Haven't fully tested it yet, but the method works for what
     * it was intended.
     *
     * @param activity The activity the app will show the toast in.
     * @param message The {@link java.lang.String} message to be displayed.
     * */
    public static void toast(final Activity activity, final String message){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                Toast t = Toast.makeText(activity, message, Toast.LENGTH_LONG);
                t.show();
            }
        });
    }

    /**
     * Adds (or removes) a favortie movie from the app's database.
     *
     * @param activity the activity I'm updating the db from.
     * @param movieId The id of the movie I'm adding or removing from the list of favorites.
     * @return An {@link android.os.AsyncTask} to update the list of favorites in the background.
     * */
    public static AsyncTask updateFavorites(final Activity activity, final int movieId, final String title){
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Uri favedMovieUri = null;
                if(!Utils.isMovieFavorite(movieId, activity.getContentResolver())){
                    ContentValues cv = new ContentValues();
                    cv.put(MovieContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
                    cv.put(MovieContract.FavoritesEntry.COLUMN_MOVIE_TITLE, title);
                    favedMovieUri = activity.getContentResolver().insert(favsUri, cv);
                }else{
                    int deletedMovies = activity.getContentResolver().delete(favsUri,
                            "id=?",
                            new String[]{String.valueOf(movieId)});
                    if(deletedMovies > 0){
                        Utils.toast(activity, "Movie removed from favorites.");
                        return null;
                    }
                }
                if(favedMovieUri != null){
                    Utils.toast(activity, "Movie added to favorites.");
                }return favedMovieUri;
            }
        };
    }

    /**
    * Attempts to show a video by launching an intent. If Youtube is installed,
    * it will use it, else it will use a browser to show the video.
    *
    * @param videoId The youtube's videoId (for now) of the video.
    * @param context the activity I'm playing the video from.
    * */
    public static void showVideoFromHere(String videoId, Context context) {
        Uri youtubeAppUri = Uri.parse(context.getString(R.string.youtube_uri) + videoId);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, youtubeAppUri);

        Uri youtubeUrl = Uri.parse(context.getString(R.string.youtube_url) + videoId);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, youtubeUrl);

        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
