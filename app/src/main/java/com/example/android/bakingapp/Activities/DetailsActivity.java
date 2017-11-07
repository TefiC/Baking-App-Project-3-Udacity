package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.bakingapp.Fragments.DetailsFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.Fragments.StepsListFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.Widgets.WidgetUtils;

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;

/**
 * Details activity for phone layout
 */

public class DetailsActivity extends AppCompatActivity {

    /*
     * Fields
     */

    private Recipe mRecipeSelected;
    private int mTabPosition;

    private static final String TAG_DETAILS_FRAGMENT = "DetailsFragment";
    private DetailsFragment mDetailsFragment;

    /*
     * Methods
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        // Remove action bar shadow and elevation
        getSupportActionBar().setElevation(0);

        if (getIntent().hasExtra("recipeObject")) {
            mRecipeSelected = getIntent().getExtras().getParcelable("recipeObject");
        }

        // For phone layouts
        if(getIntent().hasExtra("tabPosition")) {
            mTabPosition = getIntent().getExtras().getInt("tabPosition");
        }

        if (mTabletLayout) {
            setupStepsFragmentsForTablet();
        } else {
            setupDetailsFragmentForPhone();
            showUpButton();
        }
    }

    /**
     * Creates and assigns the details fragment for a phone layout
     */
    private void setupDetailsFragmentForPhone() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        mDetailsFragment = (DetailsFragment) fragmentManager.findFragmentByTag(TAG_DETAILS_FRAGMENT);

        if(mDetailsFragment == null) {

            mDetailsFragment = DetailsFragment.newInstance(mTabPosition);
            mDetailsFragment.setRecipeSelected(mRecipeSelected);

            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, mDetailsFragment, TAG_DETAILS_FRAGMENT)
                    .commit();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment_container, mDetailsFragment, TAG_DETAILS_FRAGMENT)
                    .commit();
        }
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

    /*
     * Displays an Up button
     */
    private void showUpButton() {
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /*
     * Menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        } else if (id == R.id.menu_widget) {
            item.setChecked(!item.isChecked());
            WidgetUtils.updateWidgetsData(this, mRecipeSelected);
            Log.v("MENU", "CLICKED ON MENU");
        }
        return super.onOptionsItemSelected(item);
    }
}
