package com.example.android.bakingapp.MainActivityTests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests to verify Main Activity's basic functionality on a phone
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTabletTests {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class, true, true);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void clickOnRecipe_LaunchesDetailsActivity() {
        onView(withId(R.id.main_recipes_grid_layout)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(DetailsActivity.class.getName()));
    }
}
