package com.example.android.bakingapp.MainActivityTests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.CustomAssertions.RecyclerViewNumberOfItemsAssertion;
import com.example.android.bakingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.bakingapp.Utils.FavoritesUtils.SHARED_PREFERENCES_FAV_RECIPES_KEY;

/**
 * Tests to verify Main Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicGeneralTests {

    /*
     * Constants
     */

    private static final int mNumberOfRecipes = 4;
    private Intent mIntent;
    private SharedPreferences.Editor mPreferencesEditor;
    private Context mContext;

    /*
     * Field
     */

    private IdlingResource mIdlingResource;

    /*
     * Tests set up
     */

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setSharedPreferences() {
        mIntent = new Intent();
        mContext = getInstrumentation().getTargetContext();

        mPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
    }

    /*
     * Methods
     */

    @Test
    public void activityTitle_IsDisplayedCorrectly() {
        // Set up
        launchActivity();
        registerIdlingResource();

        onView(withText("BakingApp")).check(matches(isDisplayed()));
    }

    @Test
    public void recipesList_IsPopulatedCorrectly() {
        // Set up
        launchActivity();
        registerIdlingResource();

        onView(withId(R.id.main_recipes_grid_layout)).check(new RecyclerViewNumberOfItemsAssertion(mNumberOfRecipes));
    }


    @Test
    public void clickFavoritesTab_DisplaysEmptyMessageIfNoFavoritesSelected() {
        // Set up
        launchActivity();
        registerIdlingResource();

        onView(withText("Favorites")).perform(click());
        onView(withId(R.id.no_favorites_main_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void clickFavoritesTab_DisplaysFavoriteRecipesIfNotEmpty() {

        // Set up
        addRecipeToSharedPreferences();
        launchActivity();
        registerIdlingResource();

        onView(withText("Favorites")).perform(click());
        onView(withId(R.id.no_favorites_main_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.main_favorite_recipes_grid_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // Final set up
        removeRecipeFromSharedPreferences();

    }

    @Test
    public void clickFavoriteTabAndHome_RetainsData() {
        // Set up
        launchActivity();
        registerIdlingResource();

        onView(withText("Home")).perform(click());
        onView(withId(R.id.main_recipes_grid_layout)).check(new RecyclerViewNumberOfItemsAssertion(mNumberOfRecipes));
        onView(withText("Favorites")).perform(click());
        onView(withText("Home")).perform(click());
        onView(withId(R.id.main_recipes_grid_layout)).check(new RecyclerViewNumberOfItemsAssertion(mNumberOfRecipes));
    }

    /*
     * Helper methods
     */

    /*
     * Register an idling resource to stop the test's execution until the
     * asynchronous operation is completed
     */
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());
    }

    /*
     * Launches the activity being tested
     */
    public void launchActivity() {
        intentsTestRule.launchActivity(mIntent);
    }

    /*
     * Adds the string "Cheesecake" to shared preferences
     */
    public void addRecipeToSharedPreferences() {
        HashSet<String> stringsSet = new HashSet<String>() {
        };
        stringsSet.add("Cheesecake");
        mPreferencesEditor.putStringSet(SHARED_PREFERENCES_FAV_RECIPES_KEY, stringsSet);

        mPreferencesEditor.apply();
    }

    /*
     * Removes the set of strings that represent favorite recipes
     * from Shared Preferences
     */
    public void removeRecipeFromSharedPreferences() {
        mPreferencesEditor.remove(SHARED_PREFERENCES_FAV_RECIPES_KEY);
        mPreferencesEditor.apply();
    }
}
