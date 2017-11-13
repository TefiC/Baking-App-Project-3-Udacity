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

import com.example.android.bakingapp.Adapters.MainPagerAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that displays a list of recipes
 */

public class RecipesListFragment extends Fragment {

    /*
     * Views
     */

    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.sliding_tabs) TabLayout mTabLayout;

    /*
     * Fields
     */

    public static ArrayList<Recipe> mRecipesArray = new ArrayList<Recipe>();
    private Unbinder unbinder;

    private View mRootView;

    /*
     * Methods
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.recipes_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, mRootView);

        if(savedInstanceState != null && savedInstanceState.containsKey("recipesArray")) {
            mRecipesArray = savedInstanceState.getParcelableArrayList("recipesArray");
        }

        setupTabs();

        return mRootView;
    }

    /**
     * Sets up the tabs to be displayed in the main recipes grid layout
     */
    private void setupTabs() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager.setAdapter(new MainPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));

        // Give the TabLayout the ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /*
     * Lifecycle methods
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("recipeArray", mRecipesArray);
        super.onSaveInstanceState(outState);
    }
}
