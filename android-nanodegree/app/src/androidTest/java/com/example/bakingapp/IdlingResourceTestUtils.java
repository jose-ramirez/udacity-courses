package com.example.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import okhttp3.OkHttpClient;

/**
 * Created by jose on 01/07/17.
 */

public class IdlingResourceTestUtils {

    private static IdlingResource mResource;

    /**
     * Registers an idling resource associated with an okhttp client,
     * so that espresso can run the tests after retrofit finishes
     * downloading from the cloud.
     * */
    public static void registerIdlingResource(OkHttpClient idlingClient){
        mResource = OkHttp3IdlingResource.create("OkHttp", idlingClient);
        Espresso.registerIdlingResources(mResource);
    }

    /**
     * Unregisters the idling resource, as it is no longer needed.
     * */
    public static void unregisterIdlingResource(){
        if (mResource != null) {
            Espresso.unregisterIdlingResources(mResource);
        }
    }
}
