package com.example.android.bakingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.Fragments.RecipesListFragment;
import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /*
     * Views
     */

    @Nullable
    @BindView(R.id.divider)
    View mDivider;


    /*
     * Constants
     */


    private static final String TAG_RECIPES_LIST_FRAGMENT = "recipes_list_fragment";
    private static final String TAG_COUNTING_IDLING_RESOURCE = "NEW_LOADER";


    /*
     * Fields
     */


    // Activity
    public static boolean mTabletLayout;
    public static Context mContext;

    // Testing Idling resource
    public static CountingIdlingResource mIdlingResource = new CountingIdlingResource(TAG_COUNTING_IDLING_RESOURCE);

    // Fragment
    private RecipesListFragment mRecipesListFragment;


    /*
     * Methods
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        // Bind views
        ButterKnife.bind(this);

        // Remove action bar elevation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        // Determine if the layout should be
        // for phone or tablet
        isTabletLayout();

        // Set the corresponding fragments
        setFragment();
    }

    /**
     * Determines if the layout should be for phone or tablet
     */
    private boolean isTabletLayout() {
        if (mDivider != null) {
            mTabletLayout = true;
            return true;
        } else {
            mTabletLayout = false;
            return false;
        }
    }

    /**
     * Only called from testing, creates and returns a new CountingIdlingResource
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource(TAG_COUNTING_IDLING_RESOURCE);
        }
        return mIdlingResource;
    }

    /*
     * Sets the corresponding fragment for the main activity
     */
    private void setFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mRecipesListFragment = (RecipesListFragment) fragmentManager.findFragmentByTag(TAG_RECIPES_LIST_FRAGMENT);

        if (mRecipesListFragment == null) {

            mRecipesListFragment = new RecipesListFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.master_recipes_list_fragment, mRecipesListFragment, TAG_RECIPES_LIST_FRAGMENT)
                    .commit();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.master_recipes_list_fragment, mRecipesListFragment, TAG_RECIPES_LIST_FRAGMENT)
                    .commit();
        }
    }
}
