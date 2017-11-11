package com.example.android.bakingapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.bakingapp.RecipesData.Recipe;

import java.util.HashSet;

/**
 * Helper methods for favorite recipes
 */

public class FavoritesUtils {

    /*
     * Constants
     */

    public static String SHARED_PREFERENCES_FAV_RECIPES_KEY = "favoriteRecipes";

    /*
     * Methods
     */


    /**
     * Toggles a favorite recipe by adding it or removing it from SharedPreferences and
     * by settings its attribute isFavorite
     *
     * @param context The context
     * @param recipe The recipe to be added or removed from favorites
     *
     * @return True if the recipe is not a favorite recipe and false if now it's not a favorite recipe
     */
    public static boolean toggleFavoriteRecipe(Context context, Recipe recipe) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(isRecipeFavorite(context, recipe, sharedPreferences)) {
            removeRecipeFromFavorites(context, recipe, sharedPreferences);
            recipe.setIsFavorite(false);
            Toast.makeText(context, "Removed From Favorites", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            addRecipeToFavorites(context, recipe, sharedPreferences);
            recipe.setIsFavorite(true);
            Toast.makeText(context, "Added to Favorites :)", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /**
     * Determines if a recipe is one of the user's favorites or not
     *
     * @param context The context
     * @param recipe The recipe to be analyzed
     * @param sharedPreferences An instance of SharedPreferences
     *
     * @return True if the recipe is one of the user's favorites. False if not.
     */
    public static boolean isRecipeFavorite(Context context, Recipe recipe, SharedPreferences sharedPreferences) {
        if(sharedPreferences.contains(SHARED_PREFERENCES_FAV_RECIPES_KEY)) {
            return sharedPreferences.getStringSet(SHARED_PREFERENCES_FAV_RECIPES_KEY, null).contains(recipe.getRecipeName());
        } else {
            return false;
        }
    }

    /**
     * Removes a movie from favorites by removing it from SharedPreferences
     *
     * @param context The context
     * @param recipe The recipe to be removed from favorites
     * @param sharedPreferences An instance of shared preferences
     */
    private static void removeRecipeFromFavorites(Context context, final Recipe recipe, SharedPreferences sharedPreferences) {
        sharedPreferences.getStringSet(SHARED_PREFERENCES_FAV_RECIPES_KEY, null).remove(recipe.getRecipeName());
    }

    /**
     * Adds a recipe to favorites by adding a recipe name to SharedPreferences. If there is no
     * recipe previously stored in SharedPreferences, it creates a HashSet of strings to store these values
     *
     * @param context The context
     * @param recipe The recipe to be added to favorites
     * @param sharedPreferences An instance of SharedPreferences
     */
    private static void addRecipeToFavorites(Context context, Recipe recipe, SharedPreferences sharedPreferences) {

        if(sharedPreferences.contains(SHARED_PREFERENCES_FAV_RECIPES_KEY)) {
            sharedPreferences.getStringSet(SHARED_PREFERENCES_FAV_RECIPES_KEY, null).add(recipe.getRecipeName());
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            HashSet<String> stringsSet = new HashSet<String>(){};

            stringsSet.add(recipe.getRecipeName());
            editor.putStringSet(SHARED_PREFERENCES_FAV_RECIPES_KEY, stringsSet);

            editor.apply();
        }
    }
}
