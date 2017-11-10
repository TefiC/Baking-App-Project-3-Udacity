package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipesData.Ingredient;
import com.example.android.bakingapp.Utils.RecipeDataUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An adapter for the ingredients list
 */

public class IngredientsMainAdapter extends RecyclerView.Adapter<IngredientsMainAdapter.IngredientViewHolder> {

    /*
     * Fields
     */

    private Context mContext;
    private ArrayList<Ingredient> mIngredientsList;

    /*
     * Constructor
     */

    public IngredientsMainAdapter(Context context, ArrayList<Ingredient> ingredientsList) {
        mContext = context;
        mIngredientsList = ingredientsList;
    }

    /*
     * Methods
     */

    @Override
    public IngredientsMainAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // Inflate layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutIdItem = R.layout.ingredient_item;
        boolean shouldAttachToParentImmediately = false;

        LinearLayout view = (LinearLayout) layoutInflater.inflate(layoutIdItem, parent, shouldAttachToParentImmediately);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsMainAdapter.IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredientsList.get(position);

        holder.mIngredientNumberView.setText(Integer.toString(position + 1));
        holder.mIngredientNameView.setText(RecipeDataUtils.capitalizeString(ingredient.getIngredientName()));
        holder.mIngredientQuantityUnitView.setText(ingredient.getIngredientQuantity() + " " + ingredient.getIngredientUnit());
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        /*
         * Fields
         */

        @BindView(R.id.ingredient_item_number) TextView mIngredientNumberView;
        @BindView(R.id.ingredient_item_name) TextView mIngredientNameView;
        @BindView(R.id.ingredient_item_quantity_unit) TextView mIngredientQuantityUnitView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
