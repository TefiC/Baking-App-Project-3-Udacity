package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.Fragments.StepsListFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * Details activity for phone layout
 */

public class DetailsActivity extends AppCompatActivity {

    /*
     * Fields
     */

    private Recipe mRecipeSelected;
    private int mTabPosition;

    /*
     * Methods
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        getSupportActionBar().setElevation(0);

        if (getIntent().hasExtra("recipeObject")) {
            mRecipeSelected = getIntent().getExtras().getParcelable("recipeObject");
            mTabPosition = getIntent().getExtras().getInt("tabPosition");
        }

        if (MainActivity.mTabletLayout) {
            setupStepsFragmentsForTablet();
        } else {
            setupDetailsFragmentForPhone();
        }
    }

    /**
     * Creates and assigns the details fragment for a phone layout
     */
    private void setupDetailsFragmentForPhone() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        DetailsFragment detailsFragment = DetailsFragment.newInstance(mTabPosition);
        detailsFragment.setRecipeSelected(mRecipeSelected);

        fragmentManager.beginTransaction()
                .replace(R.id.details_fragment_container, detailsFragment)
                .commit();
    }

    /*
     * Creates and assigns the details fragment for a fragments layout
     */
    private void setupStepsFragmentsForTablet() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepFragment detailsFragment = StepFragment.newInstance(mRecipeSelected.getRecipeSteps().get(0));

        fragmentManager.beginTransaction()
                .add(R.id.step_details_frame_layout, detailsFragment)
                .commit();

        StepsListFragment stepsListFragment = StepsListFragment.newInstance(mRecipeSelected);

        fragmentManager.beginTransaction()
                .add(R.id.steps_list_fragment_view, stepsListFragment)
                .commit();
    }
}
