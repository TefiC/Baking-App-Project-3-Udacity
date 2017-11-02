package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.Fragments.IngredientsFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.RecipesData.Recipe;

import java.util.ArrayList;

/**
 * Fragment Pager for the details ingredients and steps tabs
 */

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabTitles = new ArrayList<String>();
    private Context mContext;

    private Recipe mRecipeSelected;

    public DetailsPagerAdapter(FragmentManager fm, Context context,
                               Recipe recipe) {
        super(fm);
        mContext = context;
        mRecipeSelected = recipe;
        addTitleDynamically();
    }

    /**
     * Adds the necessary titles for the tabs
     */
    private void addTitleDynamically() {

        tabTitles.add("Ingredients");
        tabTitles.add("Introduction");

        for(int i = 1; i < mRecipeSelected.getRecipeSteps().size() - 1; i++) {
            tabTitles.add("Step " + i);
        }
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return IngredientsFragment.newInstance(mRecipeSelected.getRecipeName(), mRecipeSelected.getRecipeIngredients());
        } else if (position > 0 && position <= getCount()) {
            return StepFragment.newInstance(mRecipeSelected.getRecipeSteps().get(position - 1));
        } else {
            throw new UnsupportedOperationException("Tabs out of bounds: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position);
    }
}
