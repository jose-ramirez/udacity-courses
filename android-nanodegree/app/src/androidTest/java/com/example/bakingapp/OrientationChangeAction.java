/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 - Nathan Barraille
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.example.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

//import android.support.test.internal.runner.lifecycle.ActivityLifecycleMonitorRegistry;

/**
 * An Espresso ViewAction that changes the orientation of the screen.
 *
 * Got it from here: https://gist.github.com/nbarraille/03e8910dc1d415ed9740
 *
 * Usage:
 *
 * onView(isRoot()).perform(orientationLandscape());
 * onView(isRoot()).perform(orientationPortrait());
 */
public class OrientationChangeAction implements ViewAction {
    private final int orientation;

    private OrientationChangeAction(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "change orientation to " + orientation;
    }

    @Override
    public void perform(UiController uiController, View view) {
        uiController.loopMainThreadUntilIdle();

        Context viewContext = view.findViewById(android.R.id.content).getContext();
        final Activity activity = (Activity) viewContext;

        activity.setRequestedOrientation(orientation);

        // This is commented out because I still can't find out how to import
        // the ActivityLifecycleMonitorRegistry class; this serves to check
        // that the activity rotated completely, even though this action
        // works as expected, even without this check.
        /*Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry
                .getInstance().getActivitiesInStage(Stage.RESUMED);
        if (resumedActivities.isEmpty()) {
            throw new RuntimeException("Could not change orientation");
        }*/
    }

    public static ViewAction orientationLandscape() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static ViewAction orientationPortrait() {
        return new OrientationChangeAction(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}