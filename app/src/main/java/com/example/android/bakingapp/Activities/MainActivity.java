package com.example.android.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.Fragments.RecipesListFragment;
import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    private boolean mTabletLayout;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // Check the layout and avoid creating repeated fragments
        // else, use the default fragment
        if(isTabletLayout() && savedInstanceState == null) {
            assignFragments();
        } else if (!isTabletLayout()){
            addOnClickListener();
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

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_view, detailsFragment)
                .commit();
    }

    //TODO: REMOVE THIS TEST METHOD
    private void addOnClickListener() {
        Button button = findViewById(R.id.details_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
