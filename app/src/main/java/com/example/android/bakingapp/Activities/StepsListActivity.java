package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.StepsListFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * An Activity to display recipe steps
 */

public class StepsListActivity extends AppCompatActivity {

    /*
     * Fields
     */

    private Recipe mRecipeObject;

    /*
     * Methods
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_list_activity);

        if(getIntent().hasExtra("recipeObject")) {
            mRecipeObject = getIntent().getExtras().getParcelable("recipeObject");
            setupStepListFragment();
        }
    }

    private void setupStepListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepsListFragment stepsListFragment = StepsListFragment.newInstance(mRecipeObject);

        fragmentManager.beginTransaction()
                .replace(R.id.steps_list_fragment, stepsListFragment)
                .commit();
    }
}
