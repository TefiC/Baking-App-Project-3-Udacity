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

import java.util.ArrayList;

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
        holder.mIngredientNameView.setText(ingredient.getIngredientName());
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

        private TextView mIngredientNumberView;
        private TextView mIngredientNameView;
        private TextView mIngredientQuantityUnitView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            mIngredientNumberView = itemView.findViewById(R.id.ingredient_item_number);
            mIngredientNameView = itemView.findViewById(R.id.ingredient_item_name);
            mIngredientQuantityUnitView = itemView.findViewById(R.id.ingredient_item_quantity_unit);
        }
    }
}
