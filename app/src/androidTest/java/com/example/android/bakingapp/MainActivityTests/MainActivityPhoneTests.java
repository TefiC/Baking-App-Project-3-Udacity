package com.example.android.bakingapp.MainActivityTests;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests to verify Main Activity's basic functionality on a phone
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityPhoneTests {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickOnRecipe_LaunchesStepsListActivity() {

    }
}
