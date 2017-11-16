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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment to display recipe ingredients
 */

public class IngredientsFragment extends Fragment {

    /*
     * Views
     */

    @BindView(R.id.ingredients_recipe_image)
    ImageView mRecipeImageView;
    @BindView(R.id.ingredients_recipe_name)
    TextView mRecipeNameView;
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;


    /*
     * Constants
     */


    private static final String INGREDIENTS_ARRAY_KEY = "ingredients_array";
    private static final String RECIPE_NAME_KEY = "recipe_name";
    private static final String RECIPE_IMAGE_KEY = "recipe_image";


    /*
     * Fields
     */


    private String mRecipeName;
    private ArrayList<Ingredient> mIngredientsArray;
    private String mRecipeImage;

    // Butter Knife
    private Unbinder unbinder;


    /*
     * Methods
     */


    /**
     * Method that returns an instance of the fragment, adding extras
     * passed as parameters
     *
     * @param recipeName The recipe's name
     * @param ingredientArrayList The recipe's ingredients
     * @param recipeImage The recipe image URL or resource name
     *
     * @return An IngredientsFragment instance with arguments
     */
    public static IngredientsFragment newInstance(String recipeName, ArrayList<Ingredient> ingredientArrayList, String recipeImage) {

        IngredientsFragment fragment = new IngredientsFragment();

        // Add arguments
        Bundle args = new Bundle();
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

        if (ingredientsArrayList != null) {
            mIngredientsArray = ingredientsArrayList;
        }

        if (recipeImage != null) {
            mRecipeImage = recipeImage;
        }

        if (recipeName != null) {
            mRecipeName = recipeName;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        // Bind views
        unbinder = ButterKnife.bind(this, rootView);

        // Populate data
        loadRecipeImage(mRecipeImageView);
        mRecipeNameView.setText(mRecipeName);

        // Set Adapter
        setIngredientsAdapter();

        return rootView;
    }

    /**
     * Determines the correct image resource for the recipe and if the image path
     * is a URL, loads it with the Picasso library
     *
     * @param recipeImageView The ImageView where the image will be loaded
     */
    private void loadRecipeImage(ImageView recipeImageView) {
        if (mRecipeImage.substring(0, 6).equals("recipe")) {
            int resourceId = getActivity().getResources().getIdentifier(mRecipeImage, "drawable", getActivity().getPackageName());
            recipeImageView.setImageResource(resourceId);
        } else {
            Picasso.with(getActivity())
                    .load(mRecipeImage)
                    .placeholder(R.drawable.chef)
                    .error(R.drawable.chef)
                    .into(recipeImageView);
        }
        recipeImageView.setTag(mRecipeImage);
    }

    /**
     * Sets the ingredients RecyclerView adapter
     */
    private void setIngredientsAdapter() {
        // Create and apply the layout manager
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mIngredientsRecyclerView.setLayoutManager(mLinearLayoutManager);

        mIngredientsRecyclerView.setAdapter(new IngredientsMainAdapter(getActivity(), mIngredientsArray));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
