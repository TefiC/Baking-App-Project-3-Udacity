package com.example.android.bakingapp.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.squareup.picasso.Picasso;

import static com.example.android.bakingapp.Activities.MainActivity.mContext;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    /*
     * Fields
     */

    private static boolean isWidgetEmpty = true;
    private static int UNIQUE_INTENT_CODE = 5;

    /*
     * Methods
     */

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipeSelected, int[] appWidgetIds) {

        if(recipeSelected == null) {

            for(int appWidgetId : appWidgetIds) {
                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

                views.setTextViewText(R.id.appwidget_text, "EMPTY");

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);

                isWidgetEmpty = true;
            }
        } else {
            for(int appWidgetId : appWidgetIds) {
                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

                setWidgetUI(context, views, recipeSelected);

                addWidgetOnClickListeners(context, views, recipeSelected);

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);



                displayWidgetUpdatedToast(context);

                isWidgetEmpty = false;
            }
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

    private static void setWidgetUI(Context context, RemoteViews views, Recipe recipeSelected) {

        // Data
        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // UI
        views.setTextViewText(R.id.appwidget_text, recipeSelected.getRecipeName());

        loadRecipeWidgetImage(context, views, recipeSelected);

        views.setTextViewText(R.id.appwidget_servings, Integer.toString(recipeSelected.getRecipeServings()));

    }

    private static void addWidgetOnClickListeners(Context context, RemoteViews views, Recipe recipeSelected) {

        // Create an Intent to launch DetailActivity when clicked
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("recipeObject", recipeSelected);
        intent.putExtra("tabPosition", 0);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                UNIQUE_INTENT_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (recipeSelected != null) {
            views.setOnClickPendingIntent(R.id.appwidget_main_layout, pendingIntent);
        }
    }

    private static void displayWidgetUpdatedToast(Context context) {
        Toast.makeText(context, context.getString(R.string.widget_updated_toast_message), Toast.LENGTH_SHORT)
                .show();
    }

    private static void loadRecipeWidgetImage(Context context, RemoteViews views, Recipe recipe) {

        if(recipe.getRecipeImage().substring(0, 6).equals("recipe")) {
            int resourceId = context.getResources().getIdentifier(recipe.getRecipeImage(), "drawable", mContext.getPackageName());
            views.setImageViewResource(R.id.appwidget_image, resourceId);
        } else {

            // Get widget manager
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

            Picasso.with(context).load(recipe.getRecipeImage())
                    .into(views, R.id.appwidget_image, appWidgetIds);
        }
    }
}

