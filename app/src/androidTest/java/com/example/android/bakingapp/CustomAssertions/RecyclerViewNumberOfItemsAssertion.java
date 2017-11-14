package com.example.android.bakingapp.CustomAssertions;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Custom assertion to check the number of items in a RecyclerView
 */

public class RecyclerViewNumberOfItemsAssertion implements ViewAssertion {
    private final int mExpectedNumberOfItems;

    public RecyclerViewNumberOfItemsAssertion(int expectedNumberOfItems) {
        mExpectedNumberOfItems = expectedNumberOfItems;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(mExpectedNumberOfItems));
    }
}
