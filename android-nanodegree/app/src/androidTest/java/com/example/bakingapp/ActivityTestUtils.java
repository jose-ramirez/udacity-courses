package com.example.bakingapp;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by jose on 01/07/17.
 */

public class ActivityTestUtils {

    /**
     * "Wakes up" the device, for testing.
     *
     * Got it from here: https://github.com/pestrada/android-tdd-playground
     *
     * @param activity The current activity.
     * */
    public static void wakeUpDevice(final Activity activity){
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }
}
