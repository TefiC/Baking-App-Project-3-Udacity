package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.DetailsPagerAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * Fragment to display the details of the recipe selected by the user
 */

public class DetailsFragment extends Fragment {


    private Recipe mRecipeSelected;

    private ViewPager mViewPager;
    private int mCurrentTabSelected = 0;
    private static final String CURRENT_TAB_SELECTED_STRING = "current_tab_selected";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

        if(savedInstanceState != null && savedInstanceState.containsKey(CURRENT_TAB_SELECTED_STRING)) {
            mCurrentTabSelected = savedInstanceState.getInt(CURRENT_TAB_SELECTED_STRING);
        }

        if(mRecipeSelected != null) {
            setupTabs(rootView);
        }

        return rootView;
    }

    /**
     * Sets up the tabs to be displayed in the details layout
     *
     * @param rootView The tabs layout root view
     */
    private void setupTabs(View rootView) {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new DetailsPagerAdapter(getChildFragmentManager(), getActivity(), mRecipeSelected));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.details_sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /*
     * Setters
     */

    public void setRecipeSelected(Recipe recipeSelected) {
        mRecipeSelected = recipeSelected;
    }

    /*
     * Lifecycle Methods
     */

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if(mViewPager != null) {
            outState.putInt(CURRENT_TAB_SELECTED_STRING, mViewPager.getCurrentItem());
        }
        super.onSaveInstanceState(outState);
    }
}
