package com.example.bakingapp.assertions;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Used to make assertions on the number of items a RecyclerView has.
 *
 * Found here: https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso/37339656
 *
 * Usage:
 *
 * onView(withId(R.id.recyclerView)). check(withItemCount(5));
 * onView(withId(R.id.recyclerView)).check(withItemCount(greaterThan(5));
 * onView(withId(R.id.recyclerView)).check(withItemCount(lessThan(5));
 *
 */
public class RecyclerViewItemCountAssertion implements ViewAssertion {

    private final Matcher<Integer> matcher;

    public static RecyclerViewItemCountAssertion withItemCount(int expectedCount) {
        return withItemCount(is(expectedCount));
    }

    public static RecyclerViewItemCountAssertion withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewItemCountAssertion(matcher);
    }

    public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), matcher);
    }
}
