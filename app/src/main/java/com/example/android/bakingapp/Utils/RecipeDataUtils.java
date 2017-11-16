package com.example.android.bakingapp.Utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import com.example.android.bakingapp.Fragments.RecipesListFragment;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.RecipesData.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper methods to process and format recipes data
 */

public class RecipeDataUtils {


    /*
     * Methods
     */


    /**
     * Populates the RecipeListFragment's recipes array
     *
     * @param recipesJSONString Recipes data as a JSON string
     */
    public static void fillRecipesArray(Context context, String recipesJSONString) {

        try {
            JSONArray recipesJSON = new JSONArray(recipesJSONString);

            for(int i = 0; i < recipesJSON.length(); i++) {
                JSONObject recipe = recipesJSON.getJSONObject(i);
                Recipe recipeObject = createRecipeObject(recipe);
                recipeObject.setIsFavorite(FavoritesUtils.isRecipeFavorite(context, recipeObject,
                        PreferenceManager.getDefaultSharedPreferences(context)));

                RecipesListFragment.mRecipesArray.add(recipeObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Recipe object
     *
     * @param recipeJSON The recipe's data as a JSONObject
     *
     * @return A Recipe instance
     */
    private static Recipe createRecipeObject(JSONObject recipeJSON) {

        try {

            String recipeName = recipeJSON.getString("name");
            ArrayList<Ingredient> recipeIngredients = createRecipeIngredientsArray(recipeJSON.optJSONArray("ingredients"));
            ArrayList<Step> recipeSteps = createRecipeStepsArray(recipeJSON.optJSONArray("steps"));
            int recipeServings = Integer.parseInt(recipeJSON.getString("servings"));

            String recipeImage = buildRecipeName(recipeJSON);

            return new Recipe(recipeName, recipeIngredients, recipeSteps, recipeServings, recipeImage);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String buildRecipeName(JSONObject recipeJSON) {
        String recipeImage;

        try {

            if(!recipeJSON.getString("image").equals("")) {
                recipeImage = recipeJSON.getString("image");
            } else {
                recipeImage = "recipe" + recipeJSON.getInt("id");
            }

            return recipeImage;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a Steps array from the data passed as argument
     *
     * @param stepsArray A JSONArray containing the steps data
     *
     * @return An ArrayList of Steps
     */
    private static ArrayList<Step> createRecipeStepsArray(JSONArray stepsArray) {

        ArrayList<Step> recipeStepArray = new ArrayList<Step>();

        for(int i = 0; i < stepsArray.length(); i++) {
            try {
                JSONObject stepJSON = stepsArray.getJSONObject(i);
                Step recipeStep = createRecipeStep(stepJSON);

                // Add the recipe step
                recipeStepArray.add(recipeStep);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return recipeStepArray;
    }

    /**
     * Creates a Recipe Step
     *
     * @param stepJSON The Step's data as a JSONObject
     *
     * @return A Step
     */
    private static Step createRecipeStep(JSONObject stepJSON) {

        try {
            int stepId = Integer.parseInt(stepJSON.getString("id"));
            String stepShortDescription = stepJSON.getString("shortDescription");
            String stepDescription = stepJSON.getString("description");
            String stepVideoURL = stepJSON.getString("videoURL");
            String stepThumbnailURL = stepJSON.getString("thumbnailURL");

            return new Step(stepId, stepShortDescription, stepDescription, stepVideoURL, stepThumbnailURL);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a Recipes Ingredients Array from the data passed as argument
     *
     * @param ingredientsArray The ingredients data as a JSONArray
     *
     * @return An ArrayList of ingredients
     */
    private static ArrayList<Ingredient> createRecipeIngredientsArray(JSONArray ingredientsArray) {

        ArrayList<Ingredient> recipeIngredientsArray = new ArrayList<Ingredient>();

        for(int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredientJSON = null;
            try {
                ingredientJSON = ingredientsArray.getJSONObject(i);
                Ingredient recipeIngredient = createRecipeIngredient(ingredientJSON);

                // Add the recipe step
                recipeIngredientsArray.add(recipeIngredient);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return recipeIngredientsArray;
    };

    /**
     * Creates a Recipe Ingredient
     *
     * @param ingredientJSON The ingredients data as a JSONObject
     *
     * @return An Ingredient
     */
    private static Ingredient createRecipeIngredient(JSONObject ingredientJSON) {
        try {

            String ingredientName = ingredientJSON.getString("ingredient");
            String ingredientQuantity = ingredientJSON.getString("quantity");
            String ingredientMeasure = ingredientJSON.getString("measure");

            return new Ingredient(ingredientName, ingredientQuantity, ingredientMeasure);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts Dp to Pixels to use when setting LayoutParams
     *
     * @param dimensionInDp The dimension to convert
     * @param context The context of the activity that called this method
     *
     * @return The dimension passed as argument in pixels
     */
    public static int convertDpToPixels(int dimensionInDp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dimensionInDp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * Capitalizes a string by converting its first character to uppercase
     *
     * @param string The string to capitalize
     *
     * @return The string capitalized
     */
    public static String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }

    /**
     * Formats a recipe's step by removing a possible initial number at the start of the string
     *
     * @param stepDescription The step's description
     *
     * @return The Step's description without the initial number
     */
    public static String formatStepDescription(String stepDescription) {
        if(Character.isDigit(stepDescription.charAt(0))) {
            return stepDescription.substring(2, stepDescription.length());
        } else {
            return stepDescription;
        }
    }
}
