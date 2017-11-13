package com.example.android.bakingapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    /*
     * Fields
     */

    private static boolean isWidgetEmpty = true;
    public static int UNIQUE_INTENT_CODE = 5;

    public static Recipe mRecipeSelected;

    /*
     * Methods
     */

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 Recipe recipeSelected, int[] appWidgetIds) {

        // If it's the first time creating a widget, the recipe is null
        if (recipeSelected == null) {
            // If there is no previous recipe stored in memory
            if (mRecipeSelected != null) {
                setNonEmptyWidgets(context, appWidgetIds, appWidgetManager, false);
            // Else, if there is a previous recipe selected
            } else {
                setEmptyWidgetsUI(context, appWidgetIds, appWidgetManager);
            }
        // Else, if it's an update to an existing widget
        } else {
            mRecipeSelected = recipeSelected;
            setNonEmptyWidgets(context, appWidgetIds, appWidgetManager, true);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Update all widgets
        updateAppWidgets(context, appWidgetManager, null, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Sets up the UI for an empty widget with no recipe selected
     *
     * @param context The context
     * @param appWidgetIds The widget ids
     * @param appWidgetManager The widget manager
     */
    private static void setEmptyWidgetsUI(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager) {
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

            views.setTextViewText(R.id.appwidget_text, "Choose a Recipe");
            views.setTextViewTextSize(R.id.appwidget_text, COMPLEX_UNIT_SP, 16);
            views.setImageViewResource(R.id.appwidget_image, R.drawable.chef);

            views.setViewVisibility(R.id.appwidget_servings_layout, View.GONE);
            views.setViewVisibility(R.id.appwidget_ingredients_list_view, View.GONE);
            views.setViewVisibility(R.id.appwidget_empty_ingredients_list, View.VISIBLE);

            WidgetUtils.addWidgetOnClickListeners(context, views, null);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

            isWidgetEmpty = true;
        }
    }

    /**
     * Sets the UI for non empty widgets, adds its onClickListener and displays a toast to
     * the user that the widget has been set
     *
     * @param context The context
     * @param appWidgetIds The Widgets Ids
     * @param appWidgetManager The Widget manager
     */
    private static void setNonEmptyWidgets(Context context, int[] appWidgetIds,
                                           AppWidgetManager appWidgetManager, boolean displayUIMessage) {
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = getIngredientsRemoteListView(context);

            views.setViewVisibility(R.id.appwidget_servings_layout, View.VISIBLE);
            views.setViewVisibility(R.id.appwidget_ingredients_list_view, View.VISIBLE);
            views.setViewVisibility(R.id.appwidget_empty_ingredients_list, View.GONE);

            // Set UI nad listener
            WidgetUtils.setWidgetUI(context, views, mRecipeSelected);
            WidgetUtils.addWidgetOnClickListeners(context, views, mRecipeSelected);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        if(displayUIMessage) {
            // Display toast
            WidgetUtils.displayWidgetUpdatedToast(context);
        }
    }

    private static RemoteViews getIngredientsRemoteListView(Context context)  {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent = new Intent(context, IngredientsListWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_ingredients_list_view, intent);

        return views;
    }
}

