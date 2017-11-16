package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
     * Constants
     */

    private static final String RECIPES_IMAGE_RESOURCE_PREFIX = "recipe";

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

        LinearLayout view = (LinearLayout) layoutInflater.inflate(layoutIdItem, parent, shouldAttachToParentImmediately);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesMainAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipesArray.get(position);

        // Listener
        holder.setOnClickListener(holder.mRecipeCompleteView, recipe);

        // Set up data
        String recipeServings = Integer.toString(recipe.getRecipeServings());
        String recipeName = recipe.getRecipeName();

        // Populate views
        holder.mRecipeServingsView.setText(recipeServings);
        holder.mRecipesNameView.setText(recipeName);

        // Image
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

        @BindView(R.id.recipe_item_name)
        TextView mRecipesNameView;
        @BindView(R.id.recipe_item_image)
        ImageView mRecipeImageView;
        @BindView(R.id.recipe_item_servings)
        TextView mRecipeServingsView;

        /*
         * Fields
         */

        private LinearLayout mRecipeCompleteView;

        /*
         * Constructor
         */

        public RecipeViewHolder(View itemView) {
            super(itemView);

            // Bind views
            mRecipeCompleteView = (LinearLayout) itemView;
            ButterKnife.bind(this, mRecipeCompleteView);
        }

        /**
         * Sets an onClickListener to the recipe view, passing the recipe instance
         * to the onClick interface of RecipesMainAdapter to further customize
         * the actions to be performed on click
         *
         * @param recipeView The view on which to set the listener
         * @param recipe     The data that will be sent to the onClick method
         */
        private void setOnClickListener(LinearLayout recipeView, final Recipe recipe) {
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
     * @param recipe           The Recipe
     * @param recipeViewHolder The Recipe's ViewHolder
     */
    private void setImageResource(Recipe recipe, RecipeViewHolder recipeViewHolder) {
        if (recipe.getRecipeImage().substring(0, 6).equals(RECIPES_IMAGE_RESOURCE_PREFIX)) {
            int resourceId = mContext.getResources().getIdentifier(recipe.getRecipeImage(), "drawable", mContext.getPackageName());
            recipeViewHolder.mRecipeImageView.setImageResource(resourceId);
        } else {
            Picasso.with(mContext)
                    .load(recipe.getRecipeImage())
                    .placeholder(R.drawable.chef)
                    .error(R.drawable.chef)
                    .into(recipeViewHolder.mRecipeImageView);
        }
        recipeViewHolder.mRecipeImageView.setTag(recipe.getRecipeImage());
    }

    /*
     * UI interaction
     */

    /**
     * Interface to implement onClick handler for individual views
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }
}
