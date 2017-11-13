package com.example.android.bakingapp.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.squareup.picasso.Picasso;

import static com.example.android.bakingapp.Activities.MainActivity.mContext;

/**
 * Helper methods for widgets
 */

public class WidgetUtils {

    /*
     * Fields
     */

    public static final String SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY = "recipeSelectedForWidgetName";

    /*
     * Methods
     */

    /**
     * Updates the widget's data and stores it in SharedPreferences
     *
     * @param context The context
     * @param recipeSelected The recipe selected by the user
     */
    public static void updateWidgetsData(Context context, Recipe recipeSelected) {

        // Get widget manager
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_ingredients_list_view);

        // Toggle recipe name to shared preferences
        boolean isWidgetEmpty = toggleWidgetSharedPreferences(context, recipeSelected.getRecipeName());

        if(isWidgetEmpty) {
            RecipeWidgetProvider.updateAppWidgets(context, appWidgetManager, null, appWidgetIds);
        } else {
            RecipeWidgetProvider.updateAppWidgets(context, appWidgetManager, recipeSelected, appWidgetIds);
        }
    }

    /**
     * Toggles the widget's data in SharedPreferences
     *
     * @param context The context
     * @param recipeName The recipe's name
     *
     * @return True if the widget is now empty. False if it isn't.
     */
    private static boolean toggleWidgetSharedPreferences(Context context, String recipeName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

       return togglePreference(sharedPreferences, editor, SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY, recipeName);
    }

    /**
     * Toggles a single preference form SharedPreferences
     *
     * @param sharedPreferences An instance of SharedPreferences
     * @param editor The SharedPreferences editor
     * @param preferenceKey The preference key
     * @param preferenceValue The preference value
     *
     * @return True if the widget is now empty. False if it isn't.
     */
    private static boolean togglePreference(SharedPreferences sharedPreferences, SharedPreferences.Editor editor,
                                         String preferenceKey, String preferenceValue) {
        // If it's removing the same recipe previously selected
        if(sharedPreferences.contains(preferenceKey) && sharedPreferences.getString(preferenceKey, null).equals(preferenceValue)) {
            editor.remove(preferenceKey);
            RecipeWidgetProvider.mRecipeSelected = null;
            editor.apply();
            return true;
        } else {
            editor.putString(preferenceKey, preferenceValue);
            editor.apply();
            return false;
        }
    }

    /**
     * Sets the widget's User Interface
     *
     * @param context The context
     * @param views The remote views that will be updated
     * @param recipe The recipe selected by the user
     */
    public static void setWidgetUI(Context context, RemoteViews views, Recipe recipe) {

        // Data
        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // UI
        views.setTextViewText(R.id.appwidget_text, recipe.getRecipeName());

        loadRecipeWidgetImage(context, views, recipe.getRecipeImage());

        views.setTextViewText(R.id.appwidget_servings, Integer.toString(recipe.getRecipeServings()));
    }

    /**
     * Adds the widget's on click listeners depending on whether the recipe object
     * is null or not. If it is null, clicking on the widget will launch the MainActivity.
     * Else, it will launch the details activity for the recipe selected
     *
     * @param context The context
     * @param views The remote views
     * @param recipeSelected The recipe selected
     */
    public static void addWidgetOnClickListeners(Context context, RemoteViews views, Recipe recipeSelected) {

        // If there is a recipe selected, launch the corresponding details activity
        if(recipeSelected != null) {
            // Create an Intent to launch DetailActivity when clicked
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("recipeObject", recipeSelected);
            intent.putExtra("tabPosition", 0);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    RecipeWidgetProvider.UNIQUE_INTENT_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.appwidget_main_layout, pendingIntent);
        // Else, if there is no recipe selected, launch the main activity
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_main_layout, pendingIntent);
        }
    }

    /**
     * Displays a toast informing the user that the widget has been updated
     *
     * @param context The context
     */
    public static void displayWidgetUpdatedToast(Context context) {
        Toast.makeText(context, context.getString(R.string.widget_updated_toast_message), Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Loads the widget's image from Picasso or from the drawables folder
     *
     * @param context The context
     * @param views The remote views that will be updated
     * @param recipeImage The recipe image URL in String format
     */
    private static void loadRecipeWidgetImage(Context context, RemoteViews views, String recipeImage) {

        if (recipeImage.substring(0, 6).equals("recipe")) {
            int resourceId = context.getResources().getIdentifier(recipeImage, "drawable", mContext.getPackageName());
            views.setImageViewResource(R.id.appwidget_image, resourceId);
        } else {

            // Get widget manager
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

            Picasso.with(context).load(recipeImage)
                    .into(views, R.id.appwidget_image, appWidgetIds);
        }
    }

    public static boolean isRecipeWidget(Context context, String recipeName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.v("SHARED", "PREFS " + sharedPreferences.getString(WidgetUtils.SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY, null));

        if (sharedPreferences.contains(WidgetUtils.SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY)) {
            return sharedPreferences.getString(WidgetUtils.SHARED_PREFERENCES_RECIPE_NAME_WIDGET_KEY, null)
                    .equals(recipeName);
        } else {
            return false;
        }
    }
}
