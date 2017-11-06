package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment to display favorite recipes
 */

public class FavoritesFragment extends Fragment {

    /*
     * Views
     */

    @BindView(R.id.main_favorite_recipes_layout) TextView mMainRecipesLayout;

    /*
     * Fields
     */

    private String[] mFavoriteRecipesNames;
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
        View rootView = inflater.inflate(R.layout.favorites_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mMainRecipesLayout.setText("FAVORITE RECIPES");

        return rootView;
    }

    private String[] fetchFavoriteRecipesNamesFromDB() {
        return new String[]{};
    }

    private Recipe[] filterFavoriteRecipes(String[] favoriteRecipesNames, Recipe[] recipes) {
        return new Recipe[]{};
    }

    private void setFavoriteRecipesAdapter() {
        // SET ADAPTER
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
