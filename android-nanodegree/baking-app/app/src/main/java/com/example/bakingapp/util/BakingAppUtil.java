package com.example.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BakingAppUtil {
    private BakingAppUtil(){}

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
