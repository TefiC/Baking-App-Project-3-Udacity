package com.example.android.bakingapp.DetailsActivityTests;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailsActivity;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test to verify Details Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class DetailsActivityBasicTabletTests {

    private boolean mIsTabletLayout;

    @Rule
    public IntentsTestRule<DetailsActivity> intentsTestRule =
            new IntentsTestRule<>(DetailsActivity.class);

    @Test
    public void assumeTablet() {
        Assume.assumeTrue(isTablet(intentsTestRule.getActivity()));
    }


    private boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenWidthDp >= 600) ||
                (context.getResources().getConfiguration().screenHeightDp >= 600);
    }
}
