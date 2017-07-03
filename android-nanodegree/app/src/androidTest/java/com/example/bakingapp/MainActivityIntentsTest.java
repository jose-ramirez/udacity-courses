package com.example.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jose on 30/06/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityIntentsTest extends IdlingResourceTestUtils{

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule(MainActivity.class);

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
     * It should start a StepsActivity after clicking on a recipe item.
     * */
    @Test
    public void launchMainActivity_clickOnItem() {

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        intended(hasComponent(StepsActivity.class.getName()));
    }

    /**
     * Unregister the idling resources (if any).
     * */
    @After
    public void tearDown() {
        IdlingResourceTestUtils.unregisterIdlingResource();
    }
}
