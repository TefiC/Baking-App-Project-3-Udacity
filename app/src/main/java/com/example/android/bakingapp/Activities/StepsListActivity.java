package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.Fragments.StepsListFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * An Activity to display recipe steps
 */

public class StepsListActivity extends AppCompatActivity {

    /*
     * Constants
     */

    private static final String RECIPE_OBJECT_INTENT_KEY = "recipeObject";
    private static final String STEPS_LIST_FRAGMENT_UNIQUE_ID = "stepsListFragment";


    /*
     * Fields
     */


    private Recipe mRecipeObject;
    private StepsListFragment mStepsListFragment;


    /*
     * Methods
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_list_activity);

        displayUpButton();

        if (getIntent().hasExtra(RECIPE_OBJECT_INTENT_KEY)) {
            mRecipeObject = getIntent().getExtras().getParcelable(RECIPE_OBJECT_INTENT_KEY);
            setupStepListFragment();
            setTitle(getString(R.string.app_name) + " - " + mRecipeObject.getRecipeName());
        }
    }

    /*
     * Sets up the fragment to display the steps
     */
    private void setupStepListFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        mStepsListFragment = (StepsListFragment) fragmentManager.findFragmentByTag(STEPS_LIST_FRAGMENT_UNIQUE_ID);

        if(mStepsListFragment == null) {
            StepsListFragment stepsListFragment = StepsListFragment.newInstance(mRecipeObject);

            fragmentManager.beginTransaction()
                    .replace(R.id.steps_list_fragment, stepsListFragment, STEPS_LIST_FRAGMENT_UNIQUE_ID)
                    .commit();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.steps_list_fragment, mStepsListFragment, STEPS_LIST_FRAGMENT_UNIQUE_ID)
                    .commit();
        }
    }

    /*
     * Displays a button for up navigation from this activity
     */
    private void displayUpButton() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
