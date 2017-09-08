package com.example.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.model.RecipesModel;
import com.example.bakingapp.presenter.RecipesPresenter;
import com.example.bakingapp.view.activity.recipes.RecipesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.bakingapp.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    private final static int DEFAULT_PAGE_SIZE = 4;

    @Rule
    public ActivityTestRule<RecipesActivity> activityRule =
            new ActivityTestRule<>(RecipesActivity.class);

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
