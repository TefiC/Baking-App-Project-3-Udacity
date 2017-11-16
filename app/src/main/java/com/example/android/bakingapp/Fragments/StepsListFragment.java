package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.Adapters.StepsListAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.RecipesData.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;

/**
 *
 */

public class StepsListFragment extends Fragment implements StepsListAdapter.StepOnClickHandler,
                                                            StepsListAdapter.IngredientOnClickHandler {

    /*
     * Views
     */

    @BindView(R.id.steps_list_recycler_view) RecyclerView mStepListRecyclerView;


    /*
     * Constants
     */

    public static final String STEP_FRAGMENT_UNIQUE_ID = "stepFragment";
    private static final String RECIPE_KEY = "recipe_key";
    private static final String RECIPE_OBJECT_INTENT_KEY = "recipeObject";
    private static final String TAB_POSITION_INTENT_KEY = "tabPosition";
    private static final String IS_TABLET_LAYOUT_INTENT_KEY = "isTabletLayout";


    /*
     * Fields
     */


    // Data
    private Recipe mRecipeSelected;

    // Activity
    private View mRootView;
    private boolean mIsTabletLayout;

    // Butter Knife
    private Unbinder unbinder;


    /*
     * Methods
     */


    /**
     * Creates a new instance of a StepsListFragment
     *
     * @param recipe The recipe selected by the user
     *
     * @return An instance of StepListFragment with arguments
     */
    public static StepsListFragment newInstance(Recipe recipe) {

        StepsListFragment fragment = new StepsListFragment();

        Bundle args = new Bundle();

        // Add arguments to the fragment
        args.putParcelable(RECIPE_KEY, recipe);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipeSelected = getArguments().getParcelable(RECIPE_KEY);

        if(recipeSelected != null) {
            mRecipeSelected = recipeSelected;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.steps_list_fragment, container, false);

        if(savedInstanceState != null && savedInstanceState.containsKey(IS_TABLET_LAYOUT_INTENT_KEY)) {
            mIsTabletLayout = savedInstanceState.getBoolean(IS_TABLET_LAYOUT_INTENT_KEY);
        }

        // Bind views
        unbinder = ButterKnife.bind(this, mRootView);
        // Set adapter
        setStepsAdapter(mStepListRecyclerView);

        return mRootView;
    }

    /**
     * Sets up the steps adapter and a divider for its items
     *
     * @param rootView The root view that will be populated
     */
    private void setStepsAdapter(RecyclerView rootView) {

        // Create and apply the layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rootView.setLayoutManager(mLinearLayoutManager);

        rootView.setAdapter(new StepsListAdapter(getActivity(),
                mRecipeSelected.getRecipeSteps(),
                mRecipeSelected.getRecipeIngredients(),
                this,
                this));

        addItemDecoration(mLinearLayoutManager, rootView);
    }

    /**
     * Adds an item decoration between the items of the RecyclerView
     *
     * @param linearLayoutManager A Linear Layout Manager
     * @param rootView The RecyclerView that contains the items separated by the decoration
     */
    private void addItemDecoration(LinearLayoutManager linearLayoutManager, RecyclerView rootView) {
        // Add a divider decoration between step items
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(rootView.getContext(),
                linearLayoutManager.getOrientation());
        rootView.addItemDecoration(mDividerItemDecoration);
    }

    /*
     * Implementation
     */

    @Override
    public void onClick(Step step, int position) {

        if(mIsTabletLayout) {
            StepFragment stepFragment = StepFragment.newInstance(step);

            getFragmentManager().beginTransaction()
                    .replace(R.id.step_details_frame_layout, stepFragment, STEP_FRAGMENT_UNIQUE_ID)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(RECIPE_OBJECT_INTENT_KEY, mRecipeSelected);
            intent.putExtra(TAB_POSITION_INTENT_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(ArrayList<Ingredient> ingredient, int position) {

        if(mTabletLayout) {
            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(mRecipeSelected.getRecipeName(),
                    mRecipeSelected.getRecipeIngredients(),
                    mRecipeSelected.getRecipeImage());

            getFragmentManager().beginTransaction()
                    .replace(R.id.step_details_frame_layout, ingredientsFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(RECIPE_OBJECT_INTENT_KEY, mRecipeSelected);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind views
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_TABLET_LAYOUT_INTENT_KEY, mIsTabletLayout);
        super.onSaveInstanceState(outState);
    }

    /*
         * Sets if the layout displayed is a tablet or a phone
         */
    public void setIsTabletLayout(boolean isTabletLayout) {
        mIsTabletLayout = isTabletLayout;
    }
}
