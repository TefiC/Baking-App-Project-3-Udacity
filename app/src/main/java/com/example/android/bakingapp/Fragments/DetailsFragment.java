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

    /*
     * Constants
     */

    private static final String TAB_SELECTED_POSITION = "tab_selected_position";

    /*
     * Fields
     */

    private Recipe mRecipeSelected;
    private ViewPager mViewPager;
    private int mCurrentTabSelected = 0;

    /*
     * Methods
     */

    /**
     * Method that returns an instance of the fragment, adding extras
     * passed as parameters
     *
     * @param tabPosition The position of the tab that corresponds
     *                    to the step selected by the user
     *
     * @return A DetailsFragment instance with extras
     */
    public static DetailsFragment newInstance(int tabPosition) {

        DetailsFragment detailsFragment = new DetailsFragment();

        // Set fragment's arguments
        Bundle args = new Bundle();
        args.putInt(TAB_SELECTED_POSITION, tabPosition);
        detailsFragment.setArguments(args);

        return detailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

        mCurrentTabSelected = getArguments().getInt(TAB_SELECTED_POSITION);

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

        mViewPager.setCurrentItem(mCurrentTabSelected);
    }

    /*
     * Setters
     */

    public void setRecipeSelected(Recipe recipeSelected) {
        mRecipeSelected = recipeSelected;
    }
}
