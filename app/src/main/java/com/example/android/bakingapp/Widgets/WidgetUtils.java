package com.example.android.bakingapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * Helper methods for widgets
 */

public class WidgetUtils {

    /*
     * Fields
     */

    public static final String SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY = "recipeSelectedForWidgetName";
    public static final String SHARED_PREFERENCES_RECIPE_URL_WIDGET_KEY = "recipeSelectedForWidgetImageURL";
    public static final String SHARED_PREFERENCES_RECIPE_SERVINGS_WIDGET_KEY = "recipeSelectedForWidgetServings";

    /*
     * Methods
     */

    public static void updateWidgetsData(Context context, Recipe recipeSelected) {

        // Get widget manager
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        // Toggle recipe name to shared preferences
        toggleWidgetSharedPreferences(context, recipeSelected.getRecipeName(),
                recipeSelected.getRecipeImage(),
                Integer.toString(recipeSelected.getRecipeServings()));

        RecipeWidgetProvider.updateAppWidgets(context, appWidgetManager, recipeSelected, appWidgetIds);
    }

    private static void toggleWidgetSharedPreferences(Context context, String recipeName, String recipeURL, String recipeServings) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        togglePreference(sharedPreferences, editor, SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY, recipeName);
        togglePreference(sharedPreferences, editor, SHARED_PREFERENCES_RECIPE_URL_WIDGET_KEY, recipeURL);
        togglePreference(sharedPreferences, editor, SHARED_PREFERENCES_RECIPE_SERVINGS_WIDGET_KEY, recipeServings);

        editor.apply();
    }

    private static void togglePreference(SharedPreferences sharedPreferences, SharedPreferences.Editor editor,
                                         String preferenceKey, String preferenceValue) {
        if(sharedPreferences.contains(preferenceKey)) {
            editor.remove(preferenceKey);
        } else {
            editor.putString(preferenceKey, preferenceValue);
        }
    }
}
