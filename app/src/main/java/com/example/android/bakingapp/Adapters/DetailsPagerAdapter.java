package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.Fragments.IngredientsFragment;
import com.example.android.bakingapp.Fragments.StepFragment;

/**
 * Fragment Pager for the details ingredients and steps tabs
 */

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Ingredients", "Step1", "Step2", "Step3", "Step4", "Step5", "Step6"};
    private Context context;

    public DetailsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return IngredientsFragment.newInstance();
        } else if (position > 0 && position <= getCount()) {
            return StepFragment.newInstance();
        } else {
            throw new UnsupportedOperationException("Tabs out of bounds: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
