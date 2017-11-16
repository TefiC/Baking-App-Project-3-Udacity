package com.example.android.bakingapp.StepsListActivityTests;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.StepsListActivity;
import com.example.android.bakingapp.CustomAssertions.RecyclerViewNumberOfItemsAssertion;
import com.example.android.bakingapp.CustomMatchers.RecyclerViewMatcher;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * General tests for StepsListActivity
 */

@RunWith(AndroidJUnit4.class)
public class StepsListGeneralTests {

    /*
     * Fields
     */

    private ArrayList<Ingredient> mIngredientsArrayList = new ArrayList<Ingredient>();
    private ArrayList<Step> mStepsArrayList = new ArrayList<Step>();

    private int mNumIngredients = 6;
    private int mNumSteps = 6;

    private Recipe mRecipe;

    /*
     * Test set up
     */

    @Rule
    public IntentsTestRule<StepsListActivity> intentsTestRule =
            new IntentsTestRule<StepsListActivity>(StepsListActivity.class, true, true) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, StepsListActivity.class);

                    RecipeDataHelperMethods.populateIngredientsArray(mIngredientsArrayList, mNumIngredients);
                    RecipeDataHelperMethods.populateStepsArray(mStepsArrayList, mNumSteps);

                    mRecipe = new Recipe("RecipeName", mIngredientsArrayList,
                            mStepsArrayList, 8, "recipe1");

                    // Sending necessary recipe data as extras
                    result.putExtra("recipeObject", mRecipe);

                    return result;
                }
            };

    /*
     * Tests
     */

    @Test
    public void stepsList_IsPopulatedCorrectly() {
        onView(withId(R.id.steps_list_recycler_view)).check(new RecyclerViewNumberOfItemsAssertion(mRecipe.getRecipeSteps().size() + 1));
    }

    @Test
    public void stepsWithVideo_ContainVideoLogo() {
        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(3))
                .check(matches(hasDescendant(withContentDescription("step_video_logo"))));
    }

    @Test
    public void stepsWithNoVideo_DoNotContainVideoLogo() {
        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(2))
                .check(matches(hasDescendant(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE))))
                .check(matches(hasDescendant(withContentDescription("step_video_logo"))));
    }

    @Test
    public void ingredientStepTitle_IsDisplayedCorrectly() {
        onView(withText("Ingredients")).check(matches(isDisplayed()));
    }

    @Test
    public void introductionStepTitle_IsDisplayedCorrectly() {
        onView(withText("Introduction")).check(matches(isDisplayed()));
    }

    @Test
    public void introductionGenericStepTitle_IsDisplayedCorrectly() {
        onView(withText("Step 1")).check(matches(isDisplayed()));
    }

    /*
     * Helper methods
     */

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
