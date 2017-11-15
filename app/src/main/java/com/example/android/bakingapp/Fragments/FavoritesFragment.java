package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.bakingapp.Activities.DetailsActivity;
import com.example.android.bakingapp.Activities.MainActivity;
import com.example.android.bakingapp.Activities.StepsListActivity;
import com.example.android.bakingapp.Adapters.RecipesMainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.example.android.bakingapp.Utils.FavoritesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.android.bakingapp.Activities.MainActivity.mTabletLayout;

/**
 * Fragment to display favorite recipes
 */

public class FavoritesFragment extends Fragment implements RecipesMainAdapter.RecipeAdapterOnClickHandler {

    /*
     * Views
     */

    @BindView(R.id.main_favorite_recipes_grid_layout) RecyclerView mMainRecipesLayout;
    @BindView(R.id.no_favorites_main_layout) LinearLayout mNoFavoritesLayout;
    private View mRootView;

    /*
     * Fields
     */

    public static ArrayList<Recipe> mFavoriteRecipesArrayList = new ArrayList<Recipe>();
    private Unbinder unbinder;

    /*
     * Methods
     */

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.favorites_fragment, container, false);
        unbinder = ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onStart() {

        populateFavorites(RecipesListFragment.mRecipesArray);

        if(mFavoriteRecipesArrayList.size() > 0) {
            mMainRecipesLayout.setVisibility(View.VISIBLE);
            setFavoriteRecipesAdapter();
        } else {
            toggleNoFavoritesScreen();
            setFavoriteRecipesAdapter();
        }
        super.onStart();
    }

    private String[] fetchFavoriteRecipesNamesFromDB() {
        return new String[]{};
    }

    private Recipe[] filterFavoriteRecipes(String[] favoriteRecipesNames, Recipe[] recipes) {
        return new Recipe[]{};
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Recipe recipe) {

        if (mTabletLayout) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("recipeObject", recipe);
            intent.putExtra("isTabletLayout", MainActivity.mTabletLayout);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), StepsListActivity.class);
            intent.putExtra("recipeObject", recipe);
            intent.putExtra("isTabletLayout", MainActivity.mTabletLayout);
            startActivity(intent);
        }
    }

    /**
     * Populates favorite movies array
     *
     * @param recipesArray The complete array of recipes without filtering favorites
     */
    private void populateFavorites(ArrayList<Recipe> recipesArray) {

        mFavoriteRecipesArrayList.clear();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (Recipe recipe : recipesArray) {
            if (FavoritesUtils.isRecipeFavorite(getActivity(), recipe, sharedPreferences)) {
                mFavoriteRecipesArrayList.add(recipe);
            }
        }
    }

    /**
     * Sets the Movie Adapter for the main layout that will contain movie posters
     */
    private void setFavoriteRecipesAdapter() {

        // Layout Manager
        setFavoriteRecipesLayoutManager(mMainRecipesLayout);

        // Create and set the adapter
        RecipesMainAdapter recipesMainAdapter = new RecipesMainAdapter(getActivity(), mFavoriteRecipesArrayList, this);

        if (mFavoriteRecipesArrayList.size() > 0) {
            mMainRecipesLayout.setAdapter(recipesMainAdapter);
        }
    }

    /**
     * Sets a grid layout manager
     */
    private void setFavoriteRecipesLayoutManager(RecyclerView recyclerView) {

        if (mTabletLayout) {
            // Create and apply the layout manager
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
        } else {
            // Create and apply the layout manager
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }
    }


    /**
     * Determines if the No Favorites screen should be displayed in case there
     * are no favorite recipes selected
     */
    private void toggleNoFavoritesScreen() {
        if(mFavoriteRecipesArrayList.size() > 0) {
            mNoFavoritesLayout.setVisibility(View.GONE);
            mMainRecipesLayout.setVisibility(View.VISIBLE);
        } else {
            mNoFavoritesLayout.setVisibility(View.VISIBLE);
            mMainRecipesLayout.setVisibility(View.GONE);
        }
    }
}
