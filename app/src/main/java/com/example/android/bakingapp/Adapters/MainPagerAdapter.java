package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.Fragments.FavoritesFragment;
import com.example.android.bakingapp.Fragments.HomeFragment;

/**
 * An adapter to handle tabs on the main recipes layout
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    /*
     * Constants
     */

    private static final String HOME_TAB_TITLE = "Home";
    private static final String FAVORITES_TAB_TITLE = "Favorites";


    /*
     * Fields
     */


    private String tabTitles[] = new String[]{HOME_TAB_TITLE, FAVORITES_TAB_TITLE};
    private Context context;


    /*
     * Constructor
     */


    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    /*
     * Methods
     */


    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return FavoritesFragment.newInstance();
            default:
                throw new UnsupportedOperationException("Tabs out of bounds: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
