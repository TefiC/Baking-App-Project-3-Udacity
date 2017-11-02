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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

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
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetailsPagerAdapter(getChildFragmentManager(), getActivity(), mRecipeSelected));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.details_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*
     * Setters
     */

    public void setRecipeSelected(Recipe recipeSelected) {
        mRecipeSelected = recipeSelected;
    }
}
