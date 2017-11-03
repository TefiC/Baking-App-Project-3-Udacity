package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Adapters.IngredientsMainAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.squareup.picasso.Picasso;

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
    private String mRecipeImage;

    private static final String INGREDIENTS_ARRAY_KEY = "ingredients_array";
    private static final String RECIPE_NAME_KEY = "recipe_name";
    private static final String RECIPE_IMAGE_KEY = "recipe_image";

    /*
     * Methods
     */

    public static IngredientsFragment newInstance(String recipeName, ArrayList<Ingredient> ingredientArrayList, String recipeImage) {

        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();

        // Add arguments to the fragment
        args.putParcelableArrayList(INGREDIENTS_ARRAY_KEY, ingredientArrayList);
        args.putString(RECIPE_NAME_KEY, recipeName);
        args.putString(RECIPE_IMAGE_KEY, recipeImage);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Ingredient> ingredientsArrayList = getArguments().getParcelableArrayList(INGREDIENTS_ARRAY_KEY);
        String recipeName = getArguments().getString(RECIPE_NAME_KEY);
        String recipeImage = getArguments().getString(RECIPE_IMAGE_KEY);

        if(ingredientsArrayList != null) {
            mIngredientsArray = ingredientsArrayList;
        }

        if(recipeImage != null) {
            mRecipeImage = recipeImage;
        }

        if(recipeName != null) {
            mRecipeName = recipeName;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        ImageView recipeImageView = rootView.findViewById(R.id.ingredients_recipe_image);
        TextView recipeNameView = rootView.findViewById(R.id.ingredients_recipe_name);

        // Populate data
        loadRecipeImage(recipeImageView);
        recipeNameView.setText(mRecipeName);

        // Adapter
        setIngredientsAdapter(rootView);

        return rootView;
    }

    /**
     * Determines the correct image resource for the recipe and if the image path
     * is a URL, loads it with the Picasso library
     *
     * @param recipeImageView The ImageView where the image will be loaded
     */
    private void loadRecipeImage(ImageView recipeImageView) {
        if(mRecipeImage.substring(0, 6).equals("recipe")) {
            int resourceId = getActivity().getResources().getIdentifier(mRecipeImage, "drawable", getActivity().getPackageName());
            recipeImageView.setImageResource(resourceId);
        } else {
            Picasso.with(getActivity()).load(mRecipeImage).into(recipeImageView);
        }
    }

    /**
     * Sets the ingredients RecyclerView adapter
     *
     * @param rootView The Ingredients root view
     */
    private void setIngredientsAdapter(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.ingredients_recycler_view);

        // Create and apply the layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(new IngredientsMainAdapter(getActivity(), mIngredientsArray));
    }
}
