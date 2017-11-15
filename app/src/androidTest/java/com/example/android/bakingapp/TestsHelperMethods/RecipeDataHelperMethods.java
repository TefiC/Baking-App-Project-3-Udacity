package com.example.android.bakingapp.TestsHelperMethods;

import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Step;

import java.util.ArrayList;

/**
 * Helper methods to populate recipes data
 */

public class RecipeDataHelperMethods {

     /*
     * Helper methods
     */

    /**
     * Populates the ingredients array with placeholder data
     */
    public static void populateIngredientsArray(ArrayList<Ingredient> ingredientArrayList, int numIngredients) {
        for(int i = 1; i < numIngredients + 1; i++) {
            ingredientArrayList.add(new Ingredient("Ingredient" + i + "Name",
                    "Q" + i,
                    "U" + i));
        }
    }

    /**
     * Populates the steps array with placeholder data
     */
    public static void populateStepsArray(ArrayList<Step> stepArrayList, int numSteps) {
        for(int i = 1; i < numSteps + 1; i++) {
            // Introduction step
            if(i==1) {
                stepArrayList.add(new Step(i,
                        "Recipe Introduction",
                        "Recipe Introduction",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                        ""));
            // Step without video URL to test when recipe step has no video assigned
            } else if (i==2) {
                stepArrayList.add(new Step(i,
                        "Step" + (i - 1) + "ShortDescription",
                        "Step" + (i - 1) + "LongDescription",
                        "",
                        ""));
            // All other steps
            } else {
                stepArrayList.add(new Step(i,
                        "Step" + (i -1) + "ShortDescription",
                        "Step" + (i - 1) + "LongDescription",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                        ""));
            }
        }
    }
}
