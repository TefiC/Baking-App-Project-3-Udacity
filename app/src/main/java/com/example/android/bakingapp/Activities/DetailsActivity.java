package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.R;

/**
 * Details activity for phone layout
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        if(savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            DetailsFragment detailsFragment = new DetailsFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, detailsFragment)
                    .commit();
        }
    }
}
