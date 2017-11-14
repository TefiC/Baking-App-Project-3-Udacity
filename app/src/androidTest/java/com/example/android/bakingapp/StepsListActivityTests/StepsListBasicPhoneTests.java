package com.example.android.bakingapp.StepsListActivityTests;

/**
 * Created by estef on 11/13/2017.
 */

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.StepsListActivity;
import com.example.android.bakingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Tests to verify StepList Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class StepsListBasicPhoneTests {

    @Rule
    public IntentsTestRule<StepsListActivity> intentsTestRule =
            new IntentsTestRule<>(StepsListActivity.class);

    @Test
    public void clickItem_LaunchesDetailsActivity() {
        onData(anything()).inAdapterView(withId(R.id.steps_list_recycler_view)).atPosition(0).perform(click());
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }


}
