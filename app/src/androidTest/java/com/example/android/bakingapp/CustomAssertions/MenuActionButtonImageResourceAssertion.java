package com.example.android.bakingapp.CustomAssertions;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;

import static junit.framework.Assert.assertTrue;

/**
 * Assertion to check if the image resource is correct
 */

public class MenuActionButtonImageResourceAssertion implements ViewAssertion {

    private final int mExpectedResourceId;

    public MenuActionButtonImageResourceAssertion(int expectedResourceId) {
        mExpectedResourceId = expectedResourceId;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        ActionMenuItemView imageView = (ActionMenuItemView) view;
        assertTrue(Integer.parseInt(imageView.getTag().toString()) == mExpectedResourceId);
    }
}