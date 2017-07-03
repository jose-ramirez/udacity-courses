package com.example.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.bakingapp.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final static int DEFAULT_PAGE_SIZE = 4;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Initial confgurations for each test:
     *
     *   Registers the idling resource needed to run the tests.
     *   "Wakes up" the device to be able to run the tests.
     * */
    @Before
    public void setUp(){
        MainActivity activity = activityRule.getActivity();
        IdlingResourceTestUtils.registerIdlingResource(activity.client);
        ActivityTestUtils.wakeUpDevice(activity);
    }

    /**
     * It should show exactly 4 recipes.
     * */
    @Test
    public void launchMainActivity_showsRecipes(){
        onView(withId(R.id.rv_recipes))
                .check(withItemCount(DEFAULT_PAGE_SIZE));
    }

    /**
     * Unregister the idling resources (if any).
     * */
    @After
    public void tearDown() {
        IdlingResourceTestUtils.unregisterIdlingResource();
    }
}
