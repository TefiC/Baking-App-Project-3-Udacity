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
import com.example.android.bakingapp.RecipesData.Ingredient;

/**
 * Fragment to display recipe ingredients
 */

public class IngredientsFragment extends Fragment {

    /*
     * Fields
     */

    private Ingredient[] mIngredientsArray;

    /*
     * Methods
     */

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        TextView textView = rootView.findViewById(R.id.ingredients_main_layout);
        textView.setText("INGREDIENTS FRAGMENT");

        return rootView;
    }
}
