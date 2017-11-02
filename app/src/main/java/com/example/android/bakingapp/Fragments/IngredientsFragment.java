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

import java.util.ArrayList;

/**
 * Fragment to display recipe ingredients
 */

public class IngredientsFragment extends Fragment {

    /*
     * Fields
     */

    private String mRecipeName;
    private ArrayList<Ingredient> mIngredientsArray;

    private static final String INGREDIENTS_ARRAY_KEY = "ingredients_array";
    private static final String RECIPE_NAME_KEY = "recipe_name";

    /*
     * Methods
     */

    public static IngredientsFragment newInstance(String recipeName, ArrayList<Ingredient> ingredientArrayList) {

        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_ARRAY_KEY, ingredientArrayList);
        args.putString(RECIPE_NAME_KEY, recipeName);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Ingredient> ingredientsArrayList = getArguments().getParcelableArrayList(INGREDIENTS_ARRAY_KEY);
        String recipeName = getArguments().getString(RECIPE_NAME_KEY);

        if(ingredientsArrayList != null) {
            mIngredientsArray = ingredientsArrayList;
        }

        if(recipeName != null) {
            mRecipeName = recipeName;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        TextView textView = rootView.findViewById(R.id.ingredients_main_layout);

        textView.append(mRecipeName);

        for(Ingredient ingredient: mIngredientsArray) {
            textView.append(ingredient.getIngredientName());
        }

        return rootView;
    }
}
