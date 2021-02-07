package com.example.bakingapp.view.activity.steps;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.R;
import com.example.bakingapp.actions.MyViewAction;
import com.example.bakingapp.actions.OrientationChangeAction;
import com.example.bakingapp.assertions.RecyclerViewItemCountAssertion;
import com.example.bakingapp.model.RecipesModel;
import com.example.bakingapp.presenter.RecipesPresenter;
import com.example.bakingapp.util.IdlingResourceTestUtils;
import com.example.bakingapp.view.activity.recipes.RecipesActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;


@RunWith(AndroidJUnit4.class)
public class StepsActivityTest {

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
     * It should show the steps of the recipe item after clicking on it.
     * */
    @Test
    public void launchMainActivity_showsRecipes(){

        onView(ViewMatchers.withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .check(matches(isDisplayed()));
    }

    /**
     * It should show at least one step of the recipe item if it has any.
     * */
    @Test
    public void launchMainActivity_showsRecipeSteps(){

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .check(new RecyclerViewItemCountAssertion(greaterThan(0)));
    }

    /**
     * It should show the video of a given step of a given recipe, with
     * the description of such step.
     * */
    @Test
    public void launchMainActivity_showsVideo_portrait(){

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.play_step_button)));

        // Here isAssignableFrom() is used instead of withId(). This is to
        // avoid espresso matching multiple views with the same id, as it
        // always finds a PlaybackControlView and a SimpleExoPlayerView
        // with the same ids in the match phase of the test.
        // idk if this is by design or because I actually used the same
        // id twice though
        onView(isAssignableFrom(SimpleExoPlayerView.class))
                .check(matches(isDisplayed()));
    }

    /**
     * The video should come with a view showing the description of the video, i.e.,
     * what the video is about.
     */
    @Test
    public void launchMainActivity_showsDescription_portrait(){

        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.play_step_button)));

        onView(withId(R.id.tv_step_description))
                .check(matches(isDisplayed()));
    }
    /**
     * It should only show the video of a step of a selected recipe,
     * since it's in landscape mode.
     * */
    @Test
    public void launchMainActivity_showsVideo_landscape(){

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.play_step_button)));

        onView(isAssignableFrom(SimpleExoPlayerView.class))
                .check(matches(isDisplayed()));
    }

    /**
     * So that no description should exist in this view.
     */
    @Test
    public void launchMainActivity_noDescription_landscape(){

        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.play_step_button)));

        onView(withId(R.id.tv_step_description))
                .check(doesNotExist());
    }

    /**
     * Unregister the idling resources (if any).
     * */
    @After
    public void tearDown() {
        IdlingResourceTestUtils.unregisterIdlingResource();
    }
}
