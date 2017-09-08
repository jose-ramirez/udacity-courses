package com.example.bakingapp.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BakingAppUtil {
    private BakingAppUtil(){}

    /**
     * Returns true if the device's current smallest width exceeds
     * the given threshold.
     *
     * In this project it is used to distinguish between a phone
     * and a tablet by assuming that for a tablet the smallest
     * width is at least 600 dp.
     *
     * @param context
     * @param width
     * */
    public static boolean sw(Context context, int width){
        Configuration config = context.getResources().getConfiguration();
        return config.smallestScreenWidthDp >= width;
    }

    /**
     * Gets the current orientation of the device.
     *
     * @param context The current activity.
     *
     * @return The orientation of the device.
     * */
    public static int orientation(Context context){
        Configuration config = context.getResources().getConfiguration();
        return config.orientation;
    }

    /**
     * Checks whether the divice is connected to the internet.
     *
     * @param context The current activity.
     *
     * @return false if and only if the device is not connected
     * to the internet; else, returns true.
     * */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo netInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
