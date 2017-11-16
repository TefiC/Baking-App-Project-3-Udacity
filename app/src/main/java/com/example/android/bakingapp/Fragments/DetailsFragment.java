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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment to display the details of the recipe selected by the user
 */

public class DetailsFragment extends Fragment {

    /*
     * Views
     */

    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.details_sliding_tabs) TabLayout mSlidingTabs;


    /*
     * Constants
     */


    private static final String TAB_SELECTED_POSITION_TAG = "tab_selected_position";
    private static final String LAST_TAB_SELECTED_POSITION_KEY = "last_tab_selected";
    private static final String TAG_RECIPE_SELECTED = "recipe_selected";


    /*
     * Fields
     */


    private Recipe mRecipeSelected;

    // Butter Knife
    private Unbinder unbinder;

    // Data that will be kept on rotation
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
     * @return A DetailsFragment instance with arguments
     */
    public static DetailsFragment newInstance(int tabPosition) {

        DetailsFragment detailsFragment = new DetailsFragment();

        // Set fragment's arguments
        Bundle args = new Bundle();
        args.putInt(TAB_SELECTED_POSITION_TAG, tabPosition);
        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain the fragment on rotation
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        if(getArguments() != null && getArguments().containsKey(TAB_SELECTED_POSITION_TAG)) {
            mCurrentTabSelected = getArguments().getInt(TAB_SELECTED_POSITION_TAG);
        }

        // Restore fragment state after rotation
        if (savedInstanceState != null
                && savedInstanceState.containsKey(LAST_TAB_SELECTED_POSITION_KEY)
                && savedInstanceState.containsKey(TAG_RECIPE_SELECTED)) {
            mCurrentTabSelected = savedInstanceState.getInt(TAB_SELECTED_POSITION_TAG);
            mRecipeSelected = savedInstanceState.getParcelable(TAG_RECIPE_SELECTED);
        }

        if (mRecipeSelected != null) {
            setupTabs();
        }

        return rootView;
    }

    /**
     * Sets up the tabs to be displayed in the details layout
     */
    private void setupTabs() {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager.setAdapter(new DetailsPagerAdapter(getChildFragmentManager(), getActivity(), mRecipeSelected));
        setTabOnClickListener();

        // Give the TabLayout the ViewPager
        mSlidingTabs.setupWithViewPager(mViewPager);

        // Set the current tab selected
        mViewPager.setCurrentItem(mCurrentTabSelected);
    }

    /**
     * Assigns a click listener to update the variable that stores
     * the position of the currently selected tab
     */
    private void setTabOnClickListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(StepFragment.mExoPlayer != null) {
                    StepFragment.mExoPlayer.setPlayWhenReady(false);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentTabSelected = position;

                if(StepFragment.mExoPlayer != null) {
                    StepFragment.mExoPlayer.setPlayWhenReady(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


    /*
     * Setters
     */


    public void setRecipeSelected(Recipe recipeSelected) {
        mRecipeSelected = recipeSelected;
    }


    /*
     * Lifecycle methods
     */


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save data
        outState.putInt(LAST_TAB_SELECTED_POSITION_KEY, mCurrentTabSelected);
        outState.putParcelable(TAG_RECIPE_SELECTED, mRecipeSelected);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
