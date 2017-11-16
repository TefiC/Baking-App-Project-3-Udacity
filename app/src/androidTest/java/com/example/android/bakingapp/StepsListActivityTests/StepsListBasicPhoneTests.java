package com.example.android.bakingapp.StepsListActivityTests;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailsActivity;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Tests to verify StepList Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class StepsListBasicPhoneTests {

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
     * Test
     */

    @Test
    public void clickItem_LaunchesDetailsActivity() {
        onView(withId(R.id.steps_list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(hasComponent(DetailsActivity.class.getName()));
    }

    @Test
    public void clickItem_SendsIntentWithDataAndTabPosition() {
        onView(withId(R.id.steps_list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        intended(allOf(hasComponent(DetailsActivity.class.getName()),
                hasExtraWithKey("recipeObject"),
                hasExtra("tabPosition", 2)));
    }

    @Test
    public void stepsList_IsPopulatedCorrectly() {
        onView(withId(R.id.steps_list_recycler_view)).check(new RecyclerViewNumberOfItemsAssertion(mRecipe.getRecipeSteps().size() + 1));
    }

    /*
     * Helper methods
     */

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
