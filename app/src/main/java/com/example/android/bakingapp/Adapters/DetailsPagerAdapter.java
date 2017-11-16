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

    /*
     * Constants
     */

    private static final String INGREDIENTS_TAB_TITLE = "Ingredients";
    private static final String INTRODUCTION_TAB_TITLE = "Introduction";


    /*
     * Fields
     */


    private ArrayList<String> tabTitles = new ArrayList<String>();
    private Context mContext;
    private Recipe mRecipeSelected;


    /*
     * Constructor
     */


    public DetailsPagerAdapter(FragmentManager fm, Context context,
                               Recipe recipe) {
        super(fm);
        mContext = context;
        mRecipeSelected = recipe;
        addTitlesDynamically();
    }


    /*
     * Methods
     */


    /**
     * Adds the necessary titles for the tabs.
     * One for the first tab, ingredients.
     * One for the second tab, Introduction.
     * One for each step with the corresponding step number.
     */
    private void addTitlesDynamically() {

        tabTitles.add(INGREDIENTS_TAB_TITLE);
        tabTitles.add(INTRODUCTION_TAB_TITLE);

        int numStepsWithoutIntroduction = mRecipeSelected.getRecipeSteps().size() - 1;

        for (int i = 1; i < numStepsWithoutIntroduction; i++) {
            tabTitles.add("Step " + i);
        }
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return IngredientsFragment.newInstance(mRecipeSelected.getRecipeName(), mRecipeSelected.getRecipeIngredients(), mRecipeSelected.getRecipeImage());
        } else if (position > 0 && position <= getCount()) {
            return StepFragment.newInstance(mRecipeSelected.getRecipeSteps().get(position - 1));
        } else {
            throw new UnsupportedOperationException("Tab out of bounds: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
