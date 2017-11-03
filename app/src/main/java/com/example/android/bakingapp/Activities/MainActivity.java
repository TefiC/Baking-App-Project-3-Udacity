package com.example.android.bakingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.Fragments.RecipesListFragment;
import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    public static boolean mTabletLayout;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        getSupportActionBar().setElevation(0);

        // Check the layout and avoid creating repeated fragments
        // else, use the default fragment
        if(isTabletLayout() && savedInstanceState == null) {
            assignFragments();
        }
    }

    /**
     * Determines if the layout should be for phone or tablet
     */
    private boolean isTabletLayout() {
        if(findViewById(R.id.divider) != null) {
            mTabletLayout = true;
            return true;
        } else {
            mTabletLayout = false;
            return false;
        }
    }

    /**
     * Assigns the corresponding fragments for the corresponding layout
     */
    private void assignFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipesListFragment recipesFragment = new RecipesListFragment();

        fragmentManager.beginTransaction()
                .add(R.id.master_recipes_list_fragment, recipesFragment)
                .commit();

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setRecipeSelected(null);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_view, detailsFragment)
                .commit();
    }
}
