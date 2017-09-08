package com.example.bakingapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.model.RecipesModel;
import com.example.bakingapp.presenter.RecipesPresenter;
import com.example.bakingapp.view.activity.recipes.RecipesActivity;
import com.example.bakingapp.view.activity.steps.StepsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

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
public class RecipesActivityIntentsTest extends IdlingResourceTestUtils{

    @Rule
    public IntentsTestRule<RecipesActivity> activityRule =
            new IntentsTestRule(RecipesActivity.class);

    /**
     * Initial confgurations for each test:
     *
     *   Registers the idling resource needed to run the tests.
     *   "Wakes up" the device to be able to run the tests.
     * */
    @Before
    public void setUp(){
        RecipesActivity activity = activityRule.getActivity();
        RecipesPresenter presenter = (RecipesPresenter) activity.recipesPresenter;
        RecipesModel model = (RecipesModel) presenter.recipesModel;
        OkHttpClient client = model.client;
        IdlingResourceTestUtils.registerIdlingResource(client);
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
