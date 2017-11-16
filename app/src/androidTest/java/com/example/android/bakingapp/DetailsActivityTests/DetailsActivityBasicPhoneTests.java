package com.example.android.bakingapp.DetailsActivityTests;

/**
 * Created by estef on 11/14/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.CustomAssertions.ImageViewCorrectResourceAssertion;
import com.example.android.bakingapp.CustomAssertions.RecipeImageCorrectResourceAssertion;
import com.example.android.bakingapp.CustomAssertions.RecyclerViewNumberOfItemsAssertion;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.RecipesData.Step;
import com.example.android.bakingapp.TestsHelperMethods.RecipeDataHelperMethods;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.bakingapp.Utils.FavoritesUtils.SHARED_PREFERENCES_FAV_RECIPES_KEY;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Test to verify Details Activity's general functionality on phones
 */

@RunWith(AndroidJUnit4.class)
public class DetailsActivityBasicPhoneTests {

     /*
     * Fields
     */

    private ArrayList<Ingredient> mIngredientsArrayList = new ArrayList<Ingredient>();
    private ArrayList<Step> mStepsArrayList = new ArrayList<Step>();

    private int mNumIngredients = 6;
    private int mNumSteps = 6;

    /*
     * Tests set up
     */

    @Rule
    public ActivityTestRule<DetailsActivity> mActivityTestRule =
            new ActivityTestRule<DetailsActivity>(DetailsActivity.class, true, true) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, DetailsActivity.class);

                    RecipeDataHelperMethods.populateIngredientsArray(mIngredientsArrayList, mNumIngredients);
                    RecipeDataHelperMethods.populateStepsArray(mStepsArrayList, mNumSteps);

                    // Recipe with the image of recipe1
                    Recipe recipe = new Recipe("RecipeName", mIngredientsArrayList,
                            mStepsArrayList, 8, "recipe1");

                    // Sending necessary recipe data as extras
                    result.putExtra("recipeObject", recipe);
                    // Simulate that the user touched on a tab that is not the default 0th tab
                    result.putExtra("tabPosition", 1);

                    result.putExtra("isTabletLayout", false);

                    return result;
                }
            };


    /*
     * Tests
     */

    @Test
    public void detailsActivityTitle_DisplaysCorrectly() {
        onView(withText("BakingApp - " + "RecipeName")).check(matches(isDisplayed()));
    }

    /*
     * Ingredients
     */

    @Test
    public void clickOnFirstTab_DisplaysIngredients() {
        onView(withText("Ingredients")).perform(click());
        onView(withId(R.id.ingredients_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void ingredientsList_IsDisplayedCorrectly() {
        onView(withText("Ingredients")).perform(click());

        onView(withId(R.id.ingredients_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withText("6")).check(matches(isDisplayed()));
        onView(withText("Ingredient6Name")).check(matches(isDisplayed()));
        onView(withText("Q1 U1")).check(matches(isDisplayed()));
    }

    @Test
    public void ingredientsList_HasCorrectNumberOfItems() {
        onView(withId(R.id.ingredients_recycler_view)).check(new RecyclerViewNumberOfItemsAssertion(mNumIngredients));
    }

    @Test
    public void ingredientsTab_DisplaysRecipeNameCorrectly() {
        onView(withText("Ingredients")).perform(click());
        onView(withText("RecipeName")).check(matches(isDisplayed()));
    }

    @Test
    public void ingredientsTab_DisplaysCorrectRecipeImage() {
        onView(withId(R.id.ingredients_recipe_image)).check(new RecipeImageCorrectResourceAssertion("recipe1"));
    }


    /*
     * Introduction step
     */

    @Test
    public void introductionTab_DisplaysVideoViewCorrectly() {
        onView(allOf(isDisplayed(), withId(R.id.step_exoplayer_view))).check(matches(isDisplayed()));
    }

    @Test
    public void introductionTab_FullScreenButtonTogglesCorrectly() {
        onView(allOf(isDisplayed(), withId(R.id.exo_fullscreen_button))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.exo_fullscreen_icon))).check(new ImageViewCorrectResourceAssertion(R.drawable.ic_fullscreen_skrink));
        onView(allOf(isDisplayed(), withId(R.id.exo_fullscreen_button))).perform(click());
        onView(allOf(isDisplayed(), withId(R.id.exo_fullscreen_icon))).check(new ImageViewCorrectResourceAssertion(R.drawable.ic_fullscreen_expand));
    }

    @Test
    public void tabPositionReceived_isSetCorrectlyAtStart() {
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnFavoriteMenuIcon_TogglesDrawableResource() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivityTestRule.getActivity());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.favorite_button), withContentDescription("Favorite"), isDisplayed()));

        if (sharedPreferences.contains(SHARED_PREFERENCES_FAV_RECIPES_KEY)) {
            assertThat(mActivityTestRule.getActivity().mFavoriteIconSelectedId, equalTo(R.drawable.favorite_selected));
            actionMenuItemView.perform(click());
            assertThat(mActivityTestRule.getActivity().mFavoriteIconSelectedId, equalTo(R.drawable.favorite_not_selected));
        } else {
            assertThat(mActivityTestRule.getActivity().mFavoriteIconSelectedId, equalTo(R.drawable.favorite_not_selected));
            actionMenuItemView.perform(click());
            assertThat(mActivityTestRule.getActivity().mFavoriteIconSelectedId, equalTo(R.drawable.favorite_selected));
        }
    }

    @Test
    public void overflowMenu_IsToggledOnClick() {

        // Open
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        ViewInteraction addToWidgetView = onView(
                allOf(withId(R.id.title), withText("Add to Widget"), isDisplayed()));
        addToWidgetView.check(matches(isDisplayed()));

        // Close
        addToWidgetView.perform(click());
        addToWidgetView.check(doesNotExist());
    }
}
