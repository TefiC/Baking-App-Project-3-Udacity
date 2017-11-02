package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

/**
 * Details activity for phone layout
 */

public class DetailsActivity extends AppCompatActivity {

    private Recipe mRecipeSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        if(getIntent().hasExtra("recipeObject")) {
            mRecipeSelected = getIntent().getExtras().getParcelable("recipeObject");
        }

        if(savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setRecipeSelected(mRecipeSelected);

            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, detailsFragment)
                    .commit();
        }
    }
}
