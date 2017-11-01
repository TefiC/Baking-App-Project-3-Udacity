package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.Fragments.FavoritesFragment;
import com.example.android.bakingapp.Fragments.MainFragment;

/**
 * An adapter to handle tabs in the main recipes gridView layout
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Home", "Favorites"};
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance();
            case 1:
                return FavoritesFragment.newInstance();
            default:
                throw new UnsupportedOperationException("Tabs out of bounds: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
