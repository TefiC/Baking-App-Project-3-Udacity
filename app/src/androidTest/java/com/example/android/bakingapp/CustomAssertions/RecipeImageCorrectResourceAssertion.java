package com.example.android.bakingapp.CustomAssertions;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.ImageView;

import static junit.framework.Assert.assertTrue;

/**
 * Assertion to check if the recipe image resource is correct
 */

public class RecipeImageCorrectResourceAssertion implements ViewAssertion {

    private final String mExpectedResourceId;

    public RecipeImageCorrectResourceAssertion(String expectedResourceId) {
        mExpectedResourceId = expectedResourceId;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        ImageView imageView = (ImageView) view;
        assertTrue(imageView.getTag().equals(mExpectedResourceId));
    }
}
