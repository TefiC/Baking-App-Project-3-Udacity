package com.example.android.bakingapp.MainActivityTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests to verify Main Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicGeneralTests {

    private IdlingResource mIdlingResource;

    @Rule public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void activityTitle_IsDisplayedCorrectly() {
        onView(withText("BakingApp")).check(matches(isDisplayed()));
    }

    @Test
    public void clickFavoritesTab_DisplaysEmptyMessageIfNoFavoritesSelected() {

    }

    @Test
    public void clickFavoritesTab_DisplaysFavoriteRecipesIfFavoritesExist() {

    }

    @Test
    public void onRotation_DataIsKept() {

    }
}
