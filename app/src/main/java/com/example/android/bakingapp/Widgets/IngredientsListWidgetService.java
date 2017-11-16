package com.example.android.bakingapp.Widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.Utils.RecipeDataUtils;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * A Remove views service to return a new remote views factory for recipe ingredients displays on the widget
 */

public class IngredientsListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListRemoteFactory(this.getApplicationContext());
    }
}

class IngredientsListRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    /*
     * Fields
     */

    private Context mContext;
    private ArrayList<Ingredient> mIngredientsArrayList;


    /*
     * Methods
     */


    public IngredientsListRemoteFactory(Context context) {
        mContext = context;

        if(RecipeWidgetProvider.mRecipeSelected != null) {
            mIngredientsArrayList = RecipeWidgetProvider.mRecipeSelected.getRecipeIngredients();
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(RecipeWidgetProvider.mRecipeSelected != null) {
            mIngredientsArrayList = RecipeWidgetProvider.mRecipeSelected.getRecipeIngredients();
        } else {
            mIngredientsArrayList = null;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredientsArrayList == null) return 0;
        return mIngredientsArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(mIngredientsArrayList == null || mIngredientsArrayList.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
        Ingredient ingredient = mIngredientsArrayList.get(i);

        String ingredientItemNumber = Integer.toString(i + 1);
        String ingredientItemName = RecipeDataUtils.capitalizeString(ingredient.getIngredientName());
        String ingredientItemQuantity = ingredient.getIngredientQuantity() + " " + ingredient.getIngredientUnit();

        // Set content
        views.setTextViewText(R.id.ingredient_item_number, ingredientItemNumber);
        views.setTextViewText(R.id.ingredient_item_name, ingredientItemName);
        views.setTextViewText(R.id.ingredient_item_quantity_unit, ingredientItemQuantity);

        if(MainActivity.mTabletLayout) {
            setTextFormatForTablet(views);
        } else {
            setTextFormatForPhone(views);
        }

        return views;
    }

    /**
     * Sets the text format for the remove views text views for tablets
     *
     * @param views RemoteViews instance that contains the views to be formatted
     */
    private void setTextFormatForTablet(RemoteViews views) {
        views.setTextViewTextSize(R.id.ingredient_item_number, COMPLEX_UNIT_SP, 12);
        views.setTextViewTextSize(R.id.ingredient_item_name, COMPLEX_UNIT_SP, 18);
        views.setTextViewTextSize(R.id.ingredient_item_quantity_unit, COMPLEX_UNIT_SP, 16);
    }

    /**
     * Sets the text format for the remove views text views for phone
     *
     * @param views RemoteViews instance that contains the views to be formatted
     */
    private void setTextFormatForPhone(RemoteViews views) {
        views.setTextViewTextSize(R.id.ingredient_item_number, COMPLEX_UNIT_SP, 12);
        views.setTextViewTextSize(R.id.ingredient_item_name, COMPLEX_UNIT_SP, 12);
        views.setTextViewTextSize(R.id.ingredient_item_quantity_unit, COMPLEX_UNIT_SP, 10);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
