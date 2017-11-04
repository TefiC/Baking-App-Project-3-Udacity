package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;

/**
 *
 */

public class StepsListFragment extends Fragment implements StepsListAdapter.StepOnClickHandler,
                                                            StepsListAdapter.IngredientOnClickHandler {

    /*
     * Fields
     */

    private Recipe mRecipeSelected;
    private static final String RECIPE_KEY = "recipe_key";

    /*
     * Methods
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
        View rootView = inflater.inflate(R.layout.steps_list_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.steps_list_recycler_view);
        setStepsAdapter(recyclerView);

        return rootView;
    }

    private void setStepsAdapter(RecyclerView rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.steps_list_recycler_view);

        // Create and apply the layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(new StepsListAdapter(getActivity(),
                mRecipeSelected.getRecipeSteps(),
                mRecipeSelected.getRecipeIngredients(),
                this,
                this));
    }

    @Override
    public void onClick(Step step, int position) {

        if(mTabletLayout) {
            StepFragment stepFragment = StepFragment.newInstance(step);

            getFragmentManager().beginTransaction()
                    .replace(R.id.step_details_frame_layout, stepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("recipeObject", mRecipeSelected);
            intent.putExtra("tabPosition", position);
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
            intent.putExtra("recipeObject", mRecipeSelected);
            startActivity(intent);
        }
    }
}
