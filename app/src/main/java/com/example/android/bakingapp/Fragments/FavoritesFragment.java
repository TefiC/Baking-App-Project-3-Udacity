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

/**
 * Fragment to display favorite recipes
 */

public class FavoritesFragment extends Fragment {

    /*
     * Fields
     */

    private String[] mFavoriteRecipesNames;

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

        TextView textView = rootView.findViewById(R.id.main_favorite_recipes_layout);
        textView.setText("FAVORITE RECIPES");

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
}
