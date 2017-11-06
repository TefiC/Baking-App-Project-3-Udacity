package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main Adapter for Recipes RecyclerView
 */

public class RecipesMainAdapter extends RecyclerView.Adapter<RecipesMainAdapter.RecipeViewHolder> {

    /*
     * Fields
     */

    private Context mContext;
    private ArrayList<Recipe> mRecipesArray;
    private final RecipeAdapterOnClickHandler mRecipeOnClickHandler;

    /*
     * Constructor
     */


    public RecipesMainAdapter(Context context, ArrayList<Recipe> recipesArray,
                              RecipeAdapterOnClickHandler recipeAdapterOnClickHandler) {
        mContext = context;
        mRecipesArray = recipesArray;
        mRecipeOnClickHandler = recipeAdapterOnClickHandler;
    }

    /*
     * Methods
     */


    @Override
    public RecipesMainAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        // Inflate layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutIdItem = R.layout.recipe_item;
        boolean shouldAttachToParentImmediately = false;

        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(layoutIdItem, parent, shouldAttachToParentImmediately);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesMainAdapter.RecipeViewHolder holder, int position) {

        Recipe recipe = mRecipesArray.get(position);

        holder.setOnClickListener(holder.mRecipeCompleteView, recipe);


        holder.mRecipeServingsView.setText(Integer.toString(recipe.getRecipeServings()));
        holder.mRecipesNameView.setText(recipe.getRecipeName());

        setImageResource(recipe, holder);
    }

    @Override
    public int getItemCount() {
        return mRecipesArray.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        /*
         * Views
         */

        private RelativeLayout mRecipeCompleteView;

        @BindView(R.id.recipe_item_name) TextView mRecipesNameView;
        @BindView(R.id.recipe_item_image) ImageView mRecipeImageView;
        @BindView(R.id.recipe_item_servings) TextView mRecipeServingsView;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            mRecipeCompleteView = (RelativeLayout) itemView;

            ButterKnife.bind(this, mRecipeCompleteView);
        }

        /**
         * Sets an onClickListener to the recipe view, passing the recipe instance
         * to the onClick method of the RecipesMainAdapter interface to further customize
         * the actions to be performed on click
         *
         * @param recipeView The view on which to set the listener
         * @param recipe     The data that will be sent to the onClick method
         */
        private void setOnClickListener(RelativeLayout recipeView, final Recipe recipe) {
            recipeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecipeOnClickHandler.onClick(recipe);
                }
            });
        }
    }

    /**
     * Determines the appropriate image resource for the recipe.
     * If the image path is a URL it loads it using the Picasso library,
     * else it find the appropriate resource in the drawables folder
     *
     * @param recipe The Recipe
     * @param recipeViewHolder The Recipe's ViewHolder
     */
    private void setImageResource(Recipe recipe, RecipeViewHolder recipeViewHolder) {
        if(recipe.getRecipeImage().substring(0, 6).equals("recipe")) {
            int resourceId = mContext.getResources().getIdentifier(recipe.getRecipeImage(), "drawable", mContext.getPackageName());
            recipeViewHolder.mRecipeImageView.setImageResource(resourceId);
        } else {
            Picasso.with(mContext).load(recipe.getRecipeImage()).into(recipeViewHolder.mRecipeImageView);
        }
    }

    /*
     * UI interaction
     */

    /**
     * Interface to implement onClick handler for each view
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }
}
