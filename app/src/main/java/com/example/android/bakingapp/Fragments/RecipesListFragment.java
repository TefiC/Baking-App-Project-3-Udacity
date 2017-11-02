package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.MainPagerAdapter;
import com.example.android.bakingapp.Adapters.RecipesMainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

import java.util.ArrayList;

/**
 * Fragment that displays a list of recipes
 */

public class RecipesListFragment extends Fragment {

    /*
     * Fields
     */

    private RecyclerView mMainListRecyclerView;
    private RecipesMainAdapter mRecipesMainAdapter;

    public static ArrayList<Recipe> mRecipesArray = new ArrayList<Recipe>();

    private View mRootView;

    /*
     * Methods
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.recipes_list_fragment, container, false);
        setupTabs(mRootView);


        return mRootView;
    }

    /**
     * Sets up the tabs to be displayed in the main recipes grid layout
     *
     * @param rootView The tabs layout root view
     */
    private void setupTabs(View rootView) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Checks if favorite recipes have been selected
     *
     * @return True if the user has favorite recipes selected.
     *         Else, false if the user has not selected favorite recipes
     */
    private static boolean favoritesExist() {
        return true;
    }



}
