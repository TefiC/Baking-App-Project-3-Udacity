package com.example.android.bakingapp.DetailsActivityTests;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.RecipesData.Step;
import com.example.android.bakingapp.TestsHelperMethods.RecipeDataHelperMethods;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Test to verify Details Activity's basic functionality
 */

@RunWith(AndroidJUnit4.class)
public class DetailsActivityBasicTabletTests {

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
    public IntentsTestRule<DetailsActivity> intentsTestRule =
            new IntentsTestRule<DetailsActivity>(DetailsActivity.class, true, true) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, DetailsActivity.class);

                    RecipeDataHelperMethods.populateIngredientsArray(mIngredientsArrayList, mNumIngredients);
                    RecipeDataHelperMethods.populateStepsArray(mStepsArrayList, mNumSteps);

                    mRecipe = new Recipe("RecipeName", mIngredientsArrayList,
                            mStepsArrayList, 8, "recipe1");

                    // Sending necessary recipe data as extras
                    result.putExtra("recipeObject", mRecipe);

                    result.putExtra("isTabletLayout", true);

                    return result;
                }
            };

    /*
     * Tests
     */

    // TODO: FIX THESE TESTS
//
//    @Test
//    public void stepsList_IsDisplayedCorrectly() {
//        onView(withId(R.id.steps_list_fragment_view)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void stepDetails_ShowsIngredientsByDefault() {
//        onView(withId(R.id.step_main_layout)).check(matches(hasDescendant(withText("RecipeName"))));
//    }
//
//    @Test
//    public void clickOnIntroductionStep_UpdatesDetailsScreen() {
//        onView(withId(R.id.step_main_layout)).check(matches(hasDescendant(withText("RecipeName"))));
//        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(1)).perform(click());
//        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void clickOnGenericStep_UpdatesDetailsScreen() {
//        onView(withId(R.id.step_details_frame_layout)).check(matches(hasDescendant(withText("RecipeName"))));
//        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(3)).perform(click());
//        onView(withText(mRecipe.getRecipeSteps().get(2).getStepDescription())).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void stepsShortDescriptionIsDisplayed() {
//        onView(withText(mRecipe.getRecipeSteps().get(2).getStepShortDescription())).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void stepsListIsPopulatedCorrectly() {
//        onView(withId(R.id.steps_list_recycler_view)).check(new RecyclerViewNumberOfItemsAssertion(mRecipe.getRecipeSteps().size() + 1));
//    }
//
//    @Test
//    public void videoLogos_AreDisplayedCorrectly() {
//        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(2))
//                .check(matches(hasDescendant(withContentDescription("step_video_logo"))));
//    }
//
//    @Test
//    public void clickOnStepWithVideo_DisplaysVideo() {
//        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(2)).perform(click());
//        onView(withId(R.id.step_exoplayer_view)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void clickOnStepWithNoVideo_DisplaysPlaceholderImage() {
//        onView(withRecyclerView(R.id.steps_list_recycler_view).atPosition(3)).perform(click());
//        onView(withId(R.id.default_step_image)).check(matches(isDisplayed()));
//    }
//
//
//     /*
//     * Helper methods
//     */
//
//    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
//        return new RecyclerViewMatcher(recyclerViewId);
//    }
}
